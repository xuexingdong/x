package cool.xxd.service.pay.domain.aggregate;

import cool.xxd.service.pay.domain.enums.MerchantApplyStatusEnum;
import lombok.Data;

@Data
public class MerchantApply {
    private Long id;
    private String merchantApplyNo;
    private String merchantName;
    private MerchantApplyStatusEnum applyStatus;
    private String applyData;
    private Long applyUserId;
    private Long approveUserId;

    public void approve(Long approveUserId) {
        this.applyStatus = MerchantApplyStatusEnum.APPROVED;
        this.approveUserId = approveUserId;
    }
}
