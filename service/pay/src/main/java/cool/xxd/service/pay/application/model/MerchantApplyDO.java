package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("pay_merchant_apply")
public class MerchantApplyDO extends XBaseDO {
    private String merchantApplyNo;
    private String merchantName;
    private String applyStatus;
    private String applyData;
    private Long userId;
}
