package cool.xxd.service.pay.domain.strategy.wechat;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import cool.xxd.infra.crypto.SignAlgorithm;
import cool.xxd.infra.crypto.SignUtil;
import cool.xxd.infra.enums.CommonEnum;
import cool.xxd.infra.random.RandomNoGenerator;
import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.MerchantPayChannel;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.domainservice.OrderLogDomainService;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import cool.xxd.service.pay.domain.strategy.PayChannelStrategy;
import cool.xxd.service.pay.domain.strategy.wechat.api.WechatPayApi;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.CloseRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.PayRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.request.RefundRequest;
import cool.xxd.service.pay.domain.strategy.wechat.api.response.*;
import cool.xxd.service.pay.domain.strategy.wechat.api.v2.BasePayRequestV2;
import cool.xxd.service.pay.domain.strategy.wechat.api.v2.MicroPayResponseV2;
import cool.xxd.service.pay.domain.strategy.wechat.api.v2.PayRequestV2;
import cool.xxd.service.pay.domain.strategy.wechat.api.v2.WechatPayApiV2;
import cool.xxd.service.pay.domain.strategy.wechat.enums.*;
import cool.xxd.service.pay.domain.utils.BigDecimalUtils;
import cool.xxd.service.pay.domain.valueobject.PayResult;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import cool.xxd.service.pay.ui.config.WechatPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static cool.xxd.service.pay.domain.strategy.Constants.PAY_CHANNEL_STRATEGY_PREFIX;

@Slf4j
@Component(PAY_CHANNEL_STRATEGY_PREFIX + "WECHAT")
@RequiredArgsConstructor
public class WechatPayChannelStrategy implements PayChannelStrategy {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter DTF_V2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final WechatPayConfig wechatPayConfig;
    private final WechatPayApi wechatPayApi;
    private final WechatPayApiV2 wechatPayApiV2;
    private final OrderLogDomainService orderLogDomainService;

    @Override
    public PayResult pay(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        if (TransModeEnum.PASSIVE_SCAN == payOrder.getTransMode()) {
            return payV2(app, merchantPayChannel, payOrder);
        }
        var payRequest = createPayRequest(app, merchantPayChannel, payOrder);
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(payRequest));
        var jsapiResponse = wechatPayApi.jsapiPay(app, payRequest);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(jsapiResponse));
        return toPayResult(app, jsapiResponse);
    }

    private PayResult payV2(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var payRequestV2 = createPayRequestV2(merchantPayChannel, payOrder);
        var payRequest = createPayRequest(app, merchantPayChannel, payOrder);
        var logId = orderLogDomainService.init(payOrder, JSON.toJSONString(payRequest));
        var microPayResponseV2 = wechatPayApiV2.microPay(payRequestV2);
        orderLogDomainService.updateResp(logId, JSON.toJSONString(microPayResponseV2));
        return toPayResult(microPayResponseV2);
    }

    private PayRequestV2 createPayRequestV2(MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        var payRequestV2 = new PayRequestV2();
        payRequestV2.setAppid(wechatPayConfig.getSpAppid());
        // payRequestV2.setSubAppid(payOrder.getSubAppid());
        payRequestV2.setMchId(wechatPayConfig.getSpMchid());
        payRequestV2.setSubMchId(wechatPayChannelConfig.getSub_mchid());
        payRequestV2.setBody(payOrder.getDescription());
        payRequestV2.setAttach(payOrder.getCustomData());
        payRequestV2.setOutTradeNo(payOrder.getPayOrderNo());
        payRequestV2.setTotalFee(BigDecimalUtils.convertYuanToCents(payOrder.getTotalAmount()));
        payRequestV2.setSpbillCreateIp("127.0.0.1");
        payRequestV2.setTimeExpire(parseLocalDateTimeV2(payOrder.getTimeExpire()));
        payRequestV2.setAuthCode(payOrder.getAuthCode());
        var wechatPayV2ApiKey = wechatPayConfig.getApiV2Key();
        appendSignData(payRequestV2, wechatPayV2ApiKey);
        return payRequestV2;
    }

    private <T extends BasePayRequestV2> void appendSignData(T payRequestV2, String wechatPayV2ApiKey) {
        var nonceStr = RandomNoGenerator.generate(32, RandomNoGenerator.CodeType.LETTER);
        payRequestV2.setNonceStr(nonceStr);
        var sign = signV2(wechatPayV2ApiKey, payRequestV2);
        payRequestV2.setSign(sign);
    }

    private String signV2(String wechatPayV2ApiKey, Object payRequestV2) {
        var map = JSON.parseObject(
                JSON.toJSONString(payRequestV2),
                new TypeReference<Map<String, String>>() {
                });
        var stringSignTemp = createLinkString(map) + "&key=" + wechatPayV2ApiKey;
        return DigestUtil.md5Hex(stringSignTemp).toUpperCase();
    }

    /**
     * 按照参数名ASCII码从小到大排序，并且拼接成字符串
     *
     * @param parameters 参数集合
     * @return 拼接后的字符串
     */
    private String createLinkString(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    private PayResult toPayResult(MicroPayResponseV2 microPayResponseV2) {
        var payResult = new PayResult();
        if (ReturnCodeEnum.SUCCESS.getCode().equals(microPayResponseV2.getReturnCode())
                && ResultCodeEnum.SUCCESS.getCode().equals(microPayResponseV2.getResultCode())
                && TradeTypeEnum.MICROPAY.getCode().equals(microPayResponseV2.getTradeType())) {
            payResult.setPayStatus(PayStatusEnum.PAID);
            payResult.setPayTime(getLocalDateTimeV2(microPayResponseV2.getTimeEnd()));
            payResult.setThirdTradeNo(microPayResponseV2.getTransactionId());
        } else {
            payResult.setPayStatus(PayStatusEnum.FAILED);
        }
        return payResult;
    }

    private PayRequest createPayRequest(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var config = app.getConfig();
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        var payRequest = new PayRequest();
        payRequest.setSpAppid(wechatPayConfig.getSpAppid());
        payRequest.setSpMchid(wechatPayConfig.getSpMchid());
        // payRequest.setSubAppid(payOrder.getSubAppid());
        payRequest.setSubMchid(wechatPayChannelConfig.getSub_mchid());
        payRequest.setDescription(payOrder.getDescription());
        payRequest.setOutTradeNo(payOrder.getPayOrderNo());
        payRequest.setTimeExpire(parseLocalDateTime(payOrder.getTimeExpire()));
        payRequest.setAttach(payOrder.getCustomData());
        payRequest.setNotifyUrl(config.get("wechat_pay_notify_url"));
        var amount = new PayRequest.Amount();
        amount.setTotal(BigDecimalUtils.convertYuanToCents(payOrder.getTotalAmount()));
        payRequest.setAmount(amount);
        var payer = new PayRequest.Payer();
        payer.setSpOpenid(payOrder.getSubOpenid());
        // payer.setSubOpenid(payOrder.getSubOpenid());
        payRequest.setPayer(payer);
        return payRequest;
    }

    private PayResult toPayResult(App app, JsapiResponse jsapiResponse) {
        var clientPayInvokeParams = buildClientPayInvokeParams(app, jsapiResponse);
        var payResult = new PayResult();
        payResult.setPayStatus(PayStatusEnum.PAYING);
        payResult.setClientPayInvokeParams(JSON.toJSONString(clientPayInvokeParams));
        return payResult;
    }

    private Map<String, String> buildClientPayInvokeParams(App app, JsapiResponse jsapiResponse) {
        var prepayId = jsapiResponse.getPrepayId();
        var config = app.getConfig();
        var base64PrivateKey = config.get("wechat_pay_private_key");
        var timestamp = String.valueOf(Instant.now().getEpochSecond());
        var nonce = RandomNoGenerator.generate(32, RandomNoGenerator.CodeType.LETTER);
        var appId = wechatPayConfig.getSpAppid();
        var packageStr = "prepay_id=" + prepayId;
        var paySign = sign(base64PrivateKey, appId, timestamp, nonce, packageStr);
        var params = new HashMap<String, String>();
        params.put("appId", appId);
        params.put("timeStamp", timestamp);
        params.put("nonceStr", nonce);
        params.put("package", packageStr);
        params.put("signType", "RSA");
        params.put("paySign", paySign);
        return params;
    }

    @Override
    public PayResult query(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        PayResponse payResponse = wechatPayApi.queryPayResultByOutTradeNo(app, payOrder.getPayOrderNo(), wechatPayConfig.getSpMchid(), wechatPayChannelConfig.getSub_mchid());
        return toPayResult(payResponse);
    }

    public PayResult toPayResult(PayResponse payResponse) {
        var payResult = new PayResult();
        payResult.setPayStatus(tradeStateToPayStatus(payResponse.getTradeState()));
        payResult.setPayTime(getLocalDateTime(payResponse.getSuccessTime()));
        payResult.setThirdTradeNo(payResponse.getTransactionId());
        return payResult;
    }

    public PayResult toPayResult(WechatPayNotifyResource wechatPayNotifyResource) {
        var payResult = new PayResult();
        payResult.setPayStatus(tradeStateToPayStatus(wechatPayNotifyResource.getTradeState()));
        payResult.setPayTime(getLocalDateTime(wechatPayNotifyResource.getSuccessTime()));
        payResult.setThirdTradeNo(wechatPayNotifyResource.getTransactionId());
        return payResult;
    }

    @Override
    public void close(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder) {
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        var closeRequest = new CloseRequest();
        closeRequest.setSpMchid(wechatPayConfig.getSpMchid());
        closeRequest.setSubMchid(wechatPayChannelConfig.getSub_mchid());
        wechatPayApi.close(app, closeRequest);
    }

    @Override
    public RefundResult refund(App app, MerchantPayChannel merchantPayChannel, PayOrder payOrder, RefundOrder
            refundOrder) {
        var config = app.getConfig();
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        var refundRequest = new RefundRequest();
        refundRequest.setSubMchid(wechatPayChannelConfig.getSub_mchid());
        refundRequest.setOutTradeNo(payOrder.getPayOrderNo());
        refundRequest.setOutRefundNo(refundOrder.getRefundOrderNo());
        refundRequest.setReason(refundOrder.getRefundReason());
        refundRequest.setNotifyUrl(config.get("wechat_pay_refund_notify_url"));
        var amount = new RefundRequest.Amount();
        amount.setRefund(BigDecimalUtils.convertYuanToCents(refundOrder.getRefundAmount()));
        amount.setTotal(BigDecimalUtils.convertYuanToCents(refundOrder.getTotalAmount()));
        amount.setCurrency("CNY");
        refundRequest.setAmount(amount);
        var refundResponse = wechatPayApi.refund(app, refundRequest);
        return toRefundResult(refundResponse);
    }

    private RefundResult toRefundResult(RefundResponse refundResponse) {
        var wechatRefundStatus = CommonEnum.of(WechatRefundStatusEnum.class, refundResponse.getStatus());
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(refundResponse.getRefundId());
        switch (wechatRefundStatus) {
            case SUCCESS -> {
                refundResult.setRefundStatus(RefundStatusEnum.SUCCESS);
                refundResult.setRefundTime(getLocalDateTime(refundResponse.getSuccessTime()));
            }
            case PROCESSING -> refundResult.setRefundStatus(RefundStatusEnum.PROCESSING);
            case CLOSED, ABNORMAL -> refundResult.setRefundStatus(RefundStatusEnum.FAILED);
        }
        return refundResult;
    }

    public RefundResult toRefundResult(WechatRefundNotifyResource wechatRefundNotifyResource) {
        var wechatRefundStatus = CommonEnum.of(WechatRefundStatusEnum.class, wechatRefundNotifyResource.getRefund_status());
        var refundResult = new RefundResult();
        refundResult.setThirdTradeNo(wechatRefundNotifyResource.getRefundId());
        switch (wechatRefundStatus) {
            case SUCCESS -> {
                refundResult.setRefundStatus(RefundStatusEnum.SUCCESS);
                refundResult.setRefundTime(getLocalDateTime(wechatRefundNotifyResource.getSuccessTime()));
            }
            case PROCESSING -> refundResult.setRefundStatus(RefundStatusEnum.PROCESSING);
            case CLOSED, ABNORMAL -> refundResult.setRefundStatus(RefundStatusEnum.FAILED);
        }
        return refundResult;
    }

    @Override
    public RefundResult queryRefund(App app, MerchantPayChannel merchantPayChannel, RefundOrder refundOrder) {
        var wechatPayChannelConfig = JSON.parseObject(merchantPayChannel.getConfig(), WechatPayChannelConfig.class);
        var refundResponse = wechatPayApi.queryRefundResult(app, refundOrder.getRefundOrderNo(), wechatPayChannelConfig.getSub_mchid());
        return toRefundResult(refundResponse);
    }

    public static String sign(String base64PrivateKey, String... data) {
        return SignUtil.sign(SignAlgorithm.SHA256withRSA, base64PrivateKey, String.join("\n", data) + "\n");
    }

    private PayStatusEnum tradeStateToPayStatus(String tradeState) {
        return switch (CommonEnum.of(WechatTradeStateEnum.class, tradeState)) {
            case SUCCESS -> PayStatusEnum.PAID;
            case REFUND -> PayStatusEnum.REFUNDED;
            case NOTPAY -> PayStatusEnum.UNPAID;
            case CLOSED -> PayStatusEnum.CLOSED;
            case REVOKED -> PayStatusEnum.REVOKED;
            case USERPAYING -> PayStatusEnum.PAYING;
            case PAYERROR -> PayStatusEnum.FAILED;
            case null -> PayStatusEnum.PAYING;
        };
    }

    private static String parseLocalDateTimeV2(LocalDateTime localDateTime) {
        return localDateTime.format(DTF_V2);
    }

    private static LocalDateTime getLocalDateTimeV2(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private static String parseLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.withNano(0).atZone(ZoneId.systemDefault()).format(DTF);
    }

    private static LocalDateTime getLocalDateTime(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }
        return OffsetDateTime.parse(timeStr).toLocalDateTime();
    }

}
