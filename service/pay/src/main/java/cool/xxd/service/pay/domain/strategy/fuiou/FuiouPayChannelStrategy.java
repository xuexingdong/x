package cool.xxd.service.pay.domain.strategy.fuiou;

import com.alibaba.fastjson2.JSON;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategy;
import cool.xxd.service.pay.domain.strategy.fuiou.api.FuiouPayApi;
import cool.xxd.service.pay.domain.strategy.fuiou.utils.FuiouUtils;
import cool.xxd.service.pay.domain.utils.BigDecimalUtils;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "FUIOU")
@RequiredArgsConstructor
public class FuiouPayChannelStrategy implements PayChannelStrategy {

    private static final String SUCCESS_RESULT_CODE = "000000";

    private final DateTimeFormatter DATE_DTF = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final DateTimeFormatter TIME_DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final OrderLogDomainService orderLogDomainService;

    private final FuiouPayApi fuiouPayApi;

    private final Map<String, FuiouPayStrategy> fuiouPayStrategyMap;

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        FuiouPayStrategy fuiouPayStrategy = fuiouPayStrategyMap.get(PAY_CHANNEL_STRATEGY_PREFIX + payOrder.getPayChannelCode() + "_" + payOrder.getTransMode().name());
        if (fuiouPayStrategy == null) {
            throw new BusinessException("未知的交易类型");
        }
        return fuiouPayStrategy.pay(app, merchantPayChannel, payOrder);
    }

    @Override
    public PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var payResultQueryRequest = new CommonQueryRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        payResultQueryRequest.setMchntOrderNo(payOrder.getPayOrderNo());
        payResultQueryRequest.setOrderType(FuiouUtils.getOrderType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        var payResultResponse = fuiouPayApi.commonQuery(app, payResultQueryRequest);
        return parseResponse(payResultResponse);
    }

    private PayResult parseResponse(CommonQueryResponse commonQueryResponse) {
        var payStatus = FuiouUtils.getPayStatus(commonQueryResponse.getTransStat());
        var payResult = new PayResult();
        payResult.setPayStatus(payStatus);
        if (commonQueryResponse.getReservedTxnFinTs() != null) {
            payResult.setPayTime(LocalDateTime.parse(commonQueryResponse.getReservedTxnFinTs(), TIME_DTF));
        }
        return payResult;
    }

    public PayResult parseResponse(FuiouPayNotifyResponse fuiouPayNotifyResponse) {
        var payResult = new PayResult();
        if (fuiouPayNotifyResponse.getResultCode().equals(SUCCESS_RESULT_CODE)) {
            payResult.setPayStatus(PayStatusEnum.PAID);
            if (fuiouPayNotifyResponse.getTxnFinTs() != null) {
                payResult.setPayTime(LocalDateTime.parse(fuiouPayNotifyResponse.getTxnFinTs(), TIME_DTF));
            }
        } else {
            payResult.setPayStatus(PayStatusEnum.PAYING);
        }
        return payResult;
    }

    @Override
    public void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var closeRequest = new CloseOrderRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        closeRequest.setMchntOrderNo(payOrder.getPayOrderNo());
        closeRequest.setOrderType(FuiouUtils.getOrderType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        closeRequest.setSubAppid(payOrder.getSubAppid());
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(closeRequest));
        var closeResponse = fuiouPayApi.closeorder(app, closeRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(closeResponse));
    }

    @Override
    public RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var refundRequest = new CommonRefundRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        refundRequest.setMchntOrderNo(refundOrder.getPayOrderNo());
        refundRequest.setOrderType(FuiouUtils.getOrderType(refundOrder.getPayTypeCode(), payOrder.getTransMode()));
        refundRequest.setRefundOrderNo(refundOrder.getRefundOrderNo());
        refundRequest.setTotalAmt(BigDecimalUtils.convertYuanToCents(refundOrder.getTotalAmount()));
        refundRequest.setRefundAmt(BigDecimalUtils.convertYuanToCents(refundOrder.getRefundAmount()));
        // TODO 30天和360天
        refundRequest.setReservedOrigiDt(payOrder.getPayTime().format(DATE_DTF));
        var logId = orderLogDomainService.init(refundOrder, JSON.toJSONString(refundRequest));
        var refundResponse = fuiouPayApi.commonRefund(app, refundRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(refundResponse));
        return toRefundResult(refundResponse);
    }

    @Override
    public RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder) {
        var appConfig = app.getConfig();
        var fuiouPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), FuiouPayChannelConfig.class);
        var refundResultQueryRequest = new RefundQueryRequest(appConfig.get("ins_cd"), fuiouPayChannelConfig);
        refundResultQueryRequest.setRefundOrderNo(refundOrder.getRefundOrderNo());
        var refundResultResponse = fuiouPayApi.refundQuery(app, refundResultQueryRequest);
        return toRefundResult(refundResultResponse);
    }

    private RefundResult toRefundResult(CommonRefundResponse commonRefundResponse) {
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(commonRefundResponse.getReservedFyTraceNo());
        if (commonRefundResponse.getResultCode().equals(SUCCESS_RESULT_CODE)) {
            refundResult.setRefundStatus(RefundStatusEnum.PROCESSING);
        } else {
            refundResult.setRefundStatus(RefundStatusEnum.FAILED);
        }
        return refundResult;
    }

    private RefundResult toRefundResult(RefundQueryResponse refundQueryResponse) {
        var refundResult = new RefundResult();
        if (refundQueryResponse.getResultCode().equals(SUCCESS_RESULT_CODE)) {
            refundResult.setThirdTradeNo(refundQueryResponse.getReservedFyTraceNo());
            switch (refundQueryResponse.getTransStat()) {
                case SUCCESS -> {
                    refundResult.setRefundStatus(RefundStatusEnum.SUCCESS);
                    refundResult.setRefundTime(LocalDate.parse(refundQueryResponse.getReservedFySettleDt(), DATE_DTF).atTime(LocalTime.now()));
                }
                case PAYERROR -> refundResult.setRefundStatus(RefundStatusEnum.FAILED);
                default -> refundResult.setRefundStatus(RefundStatusEnum.PROCESSING);
            }
        } else {
            refundResult.setRefundStatus(RefundStatusEnum.PROCESSING);
        }
        return refundResult;
    }
}
