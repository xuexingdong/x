package cool.xxd.service.pay.domain.strategy.alipay.api;

import com.alibaba.fastjson2.JSON;
import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategy;
import cool.xxd.service.pay.domain.strategy.alipay.AlipayChannelConfig;
import cool.xxd.service.pay.domain.strategy.alipay.api.enums.*;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.request.*;
import cool.xxd.service.pay.domain.strategy.alipay.api.model.response.*;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Slf4j
@Component(PAY_CHANNEL_STRATEGY_PREFIX + "ALIPAY")
@RequiredArgsConstructor
public class AlipayChannelStrategy implements PayChannelStrategy {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter GMT_REFUND_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // 通过签名字符串、签名参数（经过 base64 解码）
    private final AlipayApi alipayApi;
    private final OrderLogDomainService orderLogDomainService;

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var alipayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), AlipayChannelConfig.class);
        switch (payOrder.getTransMode()) {
            case PASSIVE_SCAN -> {
                var alipayPayRequest = createAlipayPayRequest(app, payOrder);
                var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(alipayPayRequest));
                var alipayPayResponse = alipayApi.pay(alipayChannelConfig, alipayPayRequest);
                orderLogDomainService.updateResp(logId, JSON.toJSONString(alipayPayResponse));
                return toPayResult(alipayPayResponse);
            }
            case JSAPI -> {
                var alipayCreateRequest = createAlipayCreateRequest(app, payOrder);
                var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(alipayCreateRequest));
                var alipayCreateResponse = alipayApi.create(alipayChannelConfig, alipayCreateRequest);
                orderLogDomainService.updateResp(logId, JSON.toJSONString(alipayCreateResponse));
                return toPayResult(alipayCreateResponse);
            }
            case null, default -> throw new BusinessException("暂不支持的支付方式");
        }
    }

    private AlipayPayRequest createAlipayPayRequest(App app, PayOrder payOrder) {
        var config = app.getConfig();
        var alipayPayRequest = new AlipayPayRequest();
        alipayPayRequest.setOutTradeNo(payOrder.getPayOrderNo());
        alipayPayRequest.setTotalAmount(payOrder.getTotalAmount().toPlainString());
        alipayPayRequest.setSubject(payOrder.getSubject());
        alipayPayRequest.setNotifyUrl(config.get("alipay_notify_url"));
        alipayPayRequest.setProductCode(ProductCodeEnum.FACE_TO_FACE_PAYMENT.getCode());
        alipayPayRequest.setAuthCode(payOrder.getAuthCode());
        alipayPayRequest.setScene(SceneEnum.BAR_CODE.getCode());
        return alipayPayRequest;
    }

    // 线下产品当面付推荐使用轮询的方式调用查询接口进行判断
    private PayResult toPayResult(AlipayPayResponse alipayPayResponse) {
        var payResult = new PayResult();
        payResult.setPayStatus(PayStatusEnum.PAYING);
        payResult.setThirdTradeNo(alipayPayResponse.getTradeNo());
        return payResult;
    }

    private AlipayCreateRequest createAlipayCreateRequest(App app, PayOrder payOrder) {
        var config = app.getConfig();
        var alipayCreateRequest = new AlipayCreateRequest();
        alipayCreateRequest.setOutTradeNo(payOrder.getPayOrderNo());
        alipayCreateRequest.setTotalAmount(payOrder.getTotalAmount().toPlainString());
        alipayCreateRequest.setSubject(payOrder.getSubject());
        alipayCreateRequest.setNotifyUrl(config.get("alipay_notify_url"));
        alipayCreateRequest.setProductCode(ProductCodeEnum.JSAPI_PAY.getCode());
        alipayCreateRequest.setOpAppId(payOrder.getSubAppid());
        alipayCreateRequest.setBuyerId(payOrder.getSubOpenid());
        alipayCreateRequest.setBody(payOrder.getDescription());
        alipayCreateRequest.setTimeExpire(parseLocalDateTime(payOrder.getTimeExpire()));
        alipayCreateRequest.setPassbackParams(payOrder.getCustomData());
        return alipayCreateRequest;
    }

    private PayResult toPayResult(AlipayCreateResponse alipayCreateResponse) {
        var payResult = new PayResult();
        payResult.setPayStatus(PayStatusEnum.PAYING);
        payResult.setThirdTradeNo(alipayCreateResponse.getTradeNo());
        payResult.setClientPayInvokeParams(alipayCreateResponse.getTradeNo());
        return payResult;
    }

    @Override
    public PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var alipayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), AlipayChannelConfig.class);
        var alipayQueryRequest = new AlipayQueryRequest();
        alipayQueryRequest.setOutTradeNo(payOrder.getPayOrderNo());
        var alipayQueryResponse = alipayApi.query(alipayChannelConfig, alipayQueryRequest);
        return toPayResult(alipayQueryResponse);
    }

    public PayResult toPayResult(AlipayQueryResponse alipayQueryResponse) {
        var payResult = new PayResult();
        payResult.setPayStatus(toPayStatus(alipayQueryResponse.getTradeStatus()));
        payResult.setPayTime(getLocalDateTime(alipayQueryResponse.getSendPayDate()));
        payResult.setThirdTradeNo(alipayQueryResponse.getTradeNo());
        return payResult;
    }

    public PayResult toPayResult(AlipayNotifyRequest alipayNotifyRequest) {
        var payResult = new PayResult();
        payResult.setPayStatus(toPayStatus(alipayNotifyRequest.getTradeStatus()));
        payResult.setPayTime(getLocalDateTime(alipayNotifyRequest.getGmtPayment()));
        payResult.setThirdTradeNo(alipayNotifyRequest.getTradeNo());
        return payResult;
    }

    private PayStatusEnum toPayStatus(String tradeStatus) {
        return switch (CommonEnum.of(TradeStatusEnum.class, tradeStatus)) {
            case WAIT_BUYER_PAY -> PayStatusEnum.PAYING;
            case TRADE_CLOSED -> PayStatusEnum.CLOSED;
            case TRADE_SUCCESS, TRADE_FINISHED -> PayStatusEnum.PAID;
            case null -> PayStatusEnum.PAYING;
        };
    }

    @Override
    public void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var alipayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), AlipayChannelConfig.class);
        var closeRequest = new AlipayCloseRequest();
        closeRequest.setOutTradeNo(payOrder.getPayOrderNo());
        alipayApi.close(alipayChannelConfig, closeRequest);
    }

    @Override
    public RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder) {
        var alipayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), AlipayChannelConfig.class);
        var refundRequest = new AlipayRefundRequest();
        refundRequest.setRefundAmount(refundOrder.getRefundAmount().toPlainString());
        refundRequest.setOutTradeNo(refundOrder.getPayOrderNo());
        refundRequest.setRefundReason(refundOrder.getRefundReason());
        refundRequest.setOutRequestNo(refundOrder.getRefundOrderNo());
        var refundResponse = alipayApi.refund(alipayChannelConfig, refundRequest);
        return toRefundResult(refundResponse);
    }

    private RefundResult toRefundResult(AlipayRefundResponse alipayRefundResponse) {
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(alipayRefundResponse.getTradeNo());
        refundResult.setRefundStatus(fundChargeToRefundStatus(alipayRefundResponse.getFundChange()));
        refundResult.setRefundTime(LocalDateTime.now());
        return refundResult;
    }

    private RefundStatusEnum fundChargeToRefundStatus(String fundCharge) {
        return switch (CommonEnum.of(FundChargeEnum.class, fundCharge)) {
            case Y -> RefundStatusEnum.SUCCESS;
            case N -> RefundStatusEnum.PROCESSING;
            case null -> RefundStatusEnum.PROCESSING;
        };
    }

    @Override
    public RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder) {
        var alipayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), AlipayChannelConfig.class);
        var refundQueryRequest = new AlipayRefundQueryRequest();
        refundQueryRequest.setOutRequestNo(refundOrder.getRefundOrderNo());
        refundQueryRequest.setOutTradeNo(refundOrder.getPayOrderNo());
        refundQueryRequest.setQueryOptions(List.of("gmt_refund_pay"));
        var refundQueryResponse = alipayApi.queryRefund(alipayChannelConfig, refundQueryRequest);
        return toRefundResult(refundQueryResponse);
    }

    public RefundResult toRefundResult(AlipayRefundQueryResponse alipayRefundQueryResponse) {
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(alipayRefundQueryResponse.getTradeNo());
        refundResult.setRefundStatus(refundStatusToRefundStatus(alipayRefundQueryResponse.getRefundStatus()));
        refundResult.setRefundTime(getLocalDateTime(alipayRefundQueryResponse.getGmtRefundPay()));
        return refundResult;
    }

    private RefundStatusEnum refundStatusToRefundStatus(String refundStatus) {
        return switch (CommonEnum.of(AlipayRefundStatusEnum.class, refundStatus)) {
            case REFUND_SUCCESS -> RefundStatusEnum.SUCCESS;
            case null -> RefundStatusEnum.PROCESSING;
        };
    }

    public RefundResult toRefundResult(AlipayNotifyRequest alipayNotifyRequest) {
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(alipayNotifyRequest.getTradeNo());
        refundResult.setRefundStatus(tradeStatusToRefundStatus(alipayNotifyRequest.getTradeStatus()));
        refundResult.setRefundTime(getGmtRefundLocalDateTime(alipayNotifyRequest.getGmtRefund()));
        return refundResult;
    }

    private RefundStatusEnum tradeStatusToRefundStatus(String tradeStatus) {
        return switch (CommonEnum.of(TradeStatusEnum.class, tradeStatus)) {
            case WAIT_BUYER_PAY, TRADE_CLOSED -> RefundStatusEnum.PROCESSING;
            case TRADE_SUCCESS, TRADE_FINISHED -> RefundStatusEnum.SUCCESS;
            case null -> RefundStatusEnum.PROCESSING;
        };
    }

    private String parseLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DTF);
    }

    private LocalDateTime getLocalDateTime(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }
        return LocalDateTime.parse(timeStr, DTF);
    }

    // 交易退款时间。该笔交易的退款时间。格式 为 yyyy-MM-dd HH:mm:ss.SS。
    private LocalDateTime getGmtRefundLocalDateTime(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }
        return LocalDateTime.parse(timeStr, GMT_REFUND_DTF);
    }

    public boolean isRefund(AlipayNotifyRequest alipayNotifyRequest) {
        return StringUtils.isNotBlank(alipayNotifyRequest.getOutBizNo())
                && alipayNotifyRequest.getRefundFee() != null
                && alipayNotifyRequest.getGmtRefund() != null;
    }
}
