package cool.xxd.service.pay.domain.strategy.jialian;

import com.alibaba.fastjson2.JSON;
import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategy;
import cool.xxd.service.pay.domain.strategy.jialian.api.JialianPayApi;
import cool.xxd.service.pay.domain.strategy.jialian.enums.JialianPayStatusEnum;
import cool.xxd.service.pay.domain.strategy.jialian.enums.JialianPayTypeEnum;
import cool.xxd.service.pay.domain.strategy.jialian.enums.JialianRefundStatusEnum;
import cool.xxd.service.pay.domain.utils.BigDecimalUtils;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import cool.xxd.service.pay.ui.config.JialianPayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Component(PAY_CHANNEL_STRATEGY_PREFIX + "JIALIAN")
@RequiredArgsConstructor
public class JialianPayChannelStrategy implements PayChannelStrategy {

    private final JialianPayConfig jialianPayConfig;
    private final JialianPayApi jialianPayApi;
    private final OrderLogDomainService orderLogDomainService;

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var jialianPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), JialianPayChannelConfig.class);
        var createTradeRequest = new CreateTradeRequest();
        createTradeRequest.setOutTradeNo(payOrder.getPayOrderNo());
        createTradeRequest.setPayAmount(BigDecimalUtils.convertYuanToCents(payOrder.getTotalAmount()));
        createTradeRequest.setClientIp(getClientIp());
        createTradeRequest.setSettlementBodyId(jialianPayChannelConfig.getSettlementBodyId());
        var tradePayParam = new CreateTradeRequest.TradePayParam();
        tradePayParam.setPayType(toPayType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        tradePayParam.setAuthCode(payOrder.getAuthCode());
        tradePayParam.setClientAppId(payOrder.getSubAppid());
        tradePayParam.setClientUserId(payOrder.getSubOpenid());
        tradePayParam.setAttach(payOrder.getCustomData());
        createTradeRequest.setTradePayParam(tradePayParam);
        createTradeRequest.setOutNotifyUrl(jialianPayConfig.getNotifyUrl());
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(createTradeRequest));
        var tradeCreateResult = jialianPayApi.createTrade(jialianPayChannelConfig, createTradeRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(tradeCreateResult));
        return toPayResult(tradeCreateResult);
    }

    private PayResult toPayResult(TradeCreateResult tradeCreateResult) {
        var payResult = new PayResult();
        payResult.setPayStatus(convertPayStatus(tradeCreateResult.getPayStatus()));
        payResult.setThirdTradeNo(tradeCreateResult.getTradeNo());
        if (tradeCreateResult.getPayConfig() != null) {
            payResult.setQrCode(tradeCreateResult.getPayConfig().getScanQrCode());
            payResult.setClientPayInvokeParams(tradeCreateResult.getPayConfig().getPayJsApiInfo());
        }
        return payResult;
    }

    @Override
    public PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var jialianPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), JialianPayChannelConfig.class);
        var queryTradeRequest = new QueryTradeRequest();
        queryTradeRequest.setOutTradeNo(payOrder.getPayOrderNo());
        queryTradeRequest.setPayType(toPayType(payOrder.getPayTypeCode(), payOrder.getTransMode()));
        var tradeInfoDTO = jialianPayApi.queryTrade(jialianPayChannelConfig, queryTradeRequest);
        return toPayResult(tradeInfoDTO);
    }

    private PayResult toPayResult(TradeInfoDTO tradeInfoDTO) {
        var payResult = new PayResult();
        payResult.setPayStatus(convertPayStatus(tradeInfoDTO.getPayStatus()));
        payResult.setThirdTradeNo(tradeInfoDTO.getTradeNo());
        if (tradeInfoDTO.getPayConfig() != null) {
            payResult.setQrCode(tradeInfoDTO.getPayConfig().getScanQrCode());
            payResult.setClientPayInvokeParams(tradeInfoDTO.getPayConfig().getPayJsApiInfo());
        }
        return payResult;
    }

    public PayResult toPayResult(JialianPayNotifyRequest jialianPayNotifyRequest) {
        var data = JSON.parseObject(jialianPayNotifyRequest.getData(), JialianPayNotifyRequest.PayNotifyData.class);
        var payResult = new PayResult();
        payResult.setPayStatus(convertPayStatus(data.getPayStatus()));
        payResult.setThirdTradeNo(data.getTradeNo());
        return payResult;
    }

    @Override
    public void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var jialianPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), JialianPayChannelConfig.class);
        var closeTradeRequest = new CloseTradeRequest();
        closeTradeRequest.setOutTradeNo(payOrder.getPayOrderNo());
        closeTradeRequest.setClientIp(getClientIp());
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(closeTradeRequest));
        var closed = jialianPayApi.closeTrade(jialianPayChannelConfig, closeTradeRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(closed));
    }

    @Override
    public RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder refundOrder) {
        var jialianPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), JialianPayChannelConfig.class);
        var createRefundRequest = new CreateRefundRequest();
        createRefundRequest.setOutTradeNo(refundOrder.getPayOrderNo());
        createRefundRequest.setOutRefundNo(refundOrder.getRefundOrderNo());
        createRefundRequest.setClientIp(getClientIp());
        createRefundRequest.setRefundAmount(BigDecimalUtils.convertYuanToCents(refundOrder.getRefundAmount()));
        createRefundRequest.setRefundRemark(refundOrder.getRefundReason());
        createRefundRequest.setOutNotifyUrl(jialianPayConfig.getRefundNotifyUrl());
        var logId = orderLogDomainService.init(refundOrder, JSON.toJSONString(createRefundRequest));
        var refundCreateResult = jialianPayApi.createRefund(jialianPayChannelConfig, createRefundRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(refundCreateResult));
        return toRefundResult(refundCreateResult);
    }

    private RefundResult toRefundResult(RefundCreateResult refundCreateResult) {
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(refundCreateResult.getRefundNo());
        refundResult.setRefundStatus(convertRefundStatus(refundCreateResult.getRefundStatus()));
        return refundResult;
    }

    public RefundResult toRefundResult(JialianRefundNotifyRequest jialianRefundNotifyRequest) {
        var data = JSON.parseObject(jialianRefundNotifyRequest.getData(), JialianRefundNotifyRequest.RefundNotifyData.class);
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(data.getRefundNo());
        refundResult.setRefundStatus(convertRefundStatus(data.getRefundStatus()));
        return refundResult;
    }

    @Override
    public RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder) {
        var jialianPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), JialianPayChannelConfig.class);
        var queryRefundRequest = new QueryRefundRequest();
        // 注意这里用outRefundNo来查，查出一个，然后映射成单结果
        queryRefundRequest.setOutRefundNo(refundOrder.getRefundOrderNo());
        var refundQueryResult = jialianPayApi.queryRefund(jialianPayChannelConfig, queryRefundRequest);
        return toRefundResult(refundOrder, refundQueryResult);
    }

    private RefundResult toRefundResult(RefundOrder refundOrder, RefundQueryResult refundQueryResult) {
        var refundResult = new RefundResult();
        refundQueryResult.getRefundDetails().stream()
                .filter(item -> item.getOutRefundNo().equals(refundOrder.getRefundOrderNo()))
                .findFirst()
                .ifPresentOrElse(
                        refundDetailDTO -> {
                            refundResult.setThirdTradeNo(refundDetailDTO.getRefundNo());
                            refundResult.setRefundStatus(convertRefundStatus(refundDetailDTO.getRefundStatus()));
                            refundResult.setRefundTime(refundDetailDTO.getRefundTime());
                        },
                        () -> refundResult.setRefundStatus(RefundStatusEnum.PROCESSING)
                );
        return refundResult;
    }

    private PayStatusEnum convertPayStatus(Integer payStatus) {
        return switch (CommonEnum.of(JialianPayStatusEnum.class, payStatus)) {
            case UNPAID -> PayStatusEnum.UNPAID;
            case PAID -> PayStatusEnum.PAID;
            case CANCELED -> PayStatusEnum.FAILED;
            case PARTIALLY_REFUNDED -> PayStatusEnum.PARTIALLY_REFUNDED;
            case REFUNDED -> PayStatusEnum.REFUNDED;
            case null -> throw new BusinessException("支付状态异常");
        };
    }

    private RefundStatusEnum convertRefundStatus(Integer refundStatus) {
        return switch (CommonEnum.of(JialianRefundStatusEnum.class, refundStatus)) {
            case INIT -> RefundStatusEnum.PROCESSING;
            case SUCCESS -> RefundStatusEnum.SUCCESS;
            case FAILED -> RefundStatusEnum.FAILED;
            case null -> throw new BusinessException("退款状态异常");
        };
    }

    /**
     * 这里注意嘉联的主扫被扫和平时理解的是反的
     *
     * @param payTypeCode
     * @param transMode
     * @return
     */
    private String toPayType(String payTypeCode, TransModeEnum transMode) {
        return switch (transMode) {
            case SCAN -> JialianPayTypeEnum.PASSIVE_SCAN.getCode();
            case PASSIVE_SCAN -> JialianPayTypeEnum.ACTIVE_SCAN.getCode();
            case JSAPI -> {
                if (payTypeCode.equals(PayTypeEnum.WECHAT_PAY.getCode())) {
                    yield JialianPayTypeEnum.WECHAT_PAY.getCode();
                } else if (payTypeCode.equals(PayTypeEnum.ALIPAY.getCode())) {
                    yield JialianPayTypeEnum.ALI_PAY.getCode();
                } else {
                    // 可以在这里处理一个默认值或抛出异常
                    throw new IllegalArgumentException("Unsupported payType for JSAPI: " + payTypeCode);
                }
            }
        };
    }

    // TODO 获取客户端ip
    private String getClientIp() {
        return "127.0.0.1";
    }
}
