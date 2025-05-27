package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mapper.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_merchant_pay_channel")
public class MerchantPayChannelDO extends BaseDO {
    private String mchid;
    private String payChannelCode;
    private String config;
}
