package cool.xxd.service.trade.domain.aggregate;

import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.trade.domain.enums.OrderPayDetailTypeEnum;
import cool.xxd.service.trade.domain.enums.RefundOrderRefundDetailRefundStatus;
import cool.xxd.service.trade.domain.valueobject.RefundOrderRefundDetailRefundResult;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundOrderRefundDetail {
    private Long id;
    private Long refundOrderId;
    private String refundDetailNo;
    private OrderPayDetailTypeEnum detailType;
    private BigDecimal refundAmount;
    private String payTypeCode;
    private String payTypeName;
    private RefundOrderRefundDetailRefundStatus refundStatus;
    private String thirdTradeNo;
    private LocalDateTime refundTime;
    private Long storeId;
    private String storeName;
    private Long merchantId;
    private String merchantName;
    // 对应的orderPayDetail的流水号
    private String payDetailNo;

    public void startRefund() {
        if (refundStatus != RefundOrderRefundDetailRefundStatus.INIT) {
            throw new BusinessException("退款单退款详情状态错误");
        }
        refundStatus = RefundOrderRefundDetailRefundStatus.PROCESSING;
    }

    public void handleRefundResult(RefundOrderRefundDetailRefundResult refundOrderRefundDetailRefundResult) {
        if (refundStatus != RefundOrderRefundDetailRefundStatus.PROCESSING) {
            throw new BusinessException("退款单退款详情状态错误");
        }
        if (refundOrderRefundDetailRefundResult.getRefundStatus() == null
                || refundOrderRefundDetailRefundResult.getRefundStatus() == RefundOrderRefundDetailRefundStatus.INIT) {
            throw new BusinessException("退款单退款详情状态错误");
        }
        if (StringUtils.isNotBlank(refundOrderRefundDetailRefundResult.getPayTypeCode())) {
            payTypeCode = refundOrderRefundDetailRefundResult.getPayTypeCode();
        }
        if (StringUtils.isNotBlank(refundOrderRefundDetailRefundResult.getPayTypeName())) {
            payTypeName = refundOrderRefundDetailRefundResult.getPayTypeName();
        }
        if (StringUtils.isNotBlank(refundOrderRefundDetailRefundResult.getThirdTradeNo())) {
            thirdTradeNo = refundOrderRefundDetailRefundResult.getThirdTradeNo();
        }
        refundStatus = refundOrderRefundDetailRefundResult.getRefundStatus();
        if (refundOrderRefundDetailRefundResult.getRefundStatus() == RefundOrderRefundDetailRefundStatus.SUCCESS) {
            refundTime = LocalDateTime.now();
        }
    }

    public void refundFailed() {
        if (refundStatus != RefundOrderRefundDetailRefundStatus.PROCESSING) {
            throw new BusinessException("退款单退款详情状态错误");
        }
        refundStatus = RefundOrderRefundDetailRefundStatus.FAILED;
    }

    public boolean isFinished() {
        return refundStatus == RefundOrderRefundDetailRefundStatus.SUCCESS
                || refundStatus == RefundOrderRefundDetailRefundStatus.FAILED;
    }
}
