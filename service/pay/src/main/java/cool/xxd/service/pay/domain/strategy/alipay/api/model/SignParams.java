package cool.xxd.service.pay.domain.strategy.alipay.api.model;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class SignParams {
    @NotNull
    private String productCodeWithhold;  // 收单产品码
    @NotNull
    private String personalProductCode;  // 个人签约产品码
    @NotNull
    private String signScene;  // 协议签约场景
    @NotNull
    private AccessParams accessParams;  // 接入参数
    private String externalAgreementNo;
    private String externalLogonId;
    private SignMerchantParams subMerchant;  // 子商户信息
    @NotNull
    private PeriodRuleParams periodRuleParams;  // 周期管控规则参数

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class AccessParams {
        @NotNull
        private String channel;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class SignMerchantParams {
        private String subMerchantId;
        private String subMerchantName;
        private String subMerchantServiceName;
        private String subMerchantServiceDescription;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class PeriodRuleParams {
        @NotNull
        private String periodType;
        @NotNull
        private Integer period;
        @NotNull
        private String executeTime;
        @NotNull
        private BigDecimal singleAmount;
        private BigDecimal totalAmount;
        private Integer totalPayments;
        private String signNotifyUrl;
    }
}