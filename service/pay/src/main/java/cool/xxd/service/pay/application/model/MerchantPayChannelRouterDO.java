package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_merchant_pay_channel_router")
public class MerchantPayChannelRouterDO extends XBaseDO {
    private String mchid;
    private String payTypeCode;
    private String payChannelCode;
}
