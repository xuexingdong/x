package cool.xxd.service.pay.domain.aggregate;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.constants.Constants;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.valueobject.RefundResult;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundOrder {
    private Long id;
    private String mchid;
    private String appid;
    private String payOrderNo;
    private String outTradeNo;
    private String refundOrderNo;
    private String outRefundNo;
    private BigDecimal totalAmount;
    private BigDecimal refundAmount;
    private String refundReason;
    private String payTypeCode;
    private String payTypeName;
    private String payChannelCode;
    private String payChannelName;
    private RefundStatusEnum refundStatus;
    private LocalDateTime pollingStartTime;
    private LocalDateTime refundTime;
    private String thirdTradeNo;

    public void handleRefundResult(RefundResult refundResult) {
        if (isFinalRefundStatus()) {
            return;
        }
        if (refundResult.getThirdTradeNo() != null) {
            thirdTradeNo = refundResult.getThirdTradeNo();
        }
        switch (refundResult.getRefundStatus()) {
            case SUCCESS -> {
                refundStatus = RefundStatusEnum.SUCCESS;
                if (refundResult.getRefundTime() != null) {
                    refundTime = refundResult.getRefundTime();
                } else {
                    refundTime = LocalDateTime.now();
                }
            }
            case FAILED -> fail();
        }
    }

    public void fail() {
        if (refundStatus != RefundStatusEnum.PROCESSING) {
            throw new BusinessException("退款状态错误");
        }
        refundStatus = RefundStatusEnum.FAILED;
    }

    public void startRefund() {
        if (refundStatus != RefundStatusEnum.INIT) {
            throw new BusinessException("退款状态错误");
        }
        refundStatus = RefundStatusEnum.PROCESSING;
        pollingStartTime = LocalDateTime.now().plusSeconds(Constants.POLLING_DELAY_SECONDS);
    }

    public boolean isFinalRefundStatus() {
        return refundStatus == RefundStatusEnum.SUCCESS
                || refundStatus == RefundStatusEnum.FAILED;
    }

}
