package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mapper.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_merchant")
public class MerchantDO extends BaseDO {
    private String appid;
    private String mchid;
    private String name;
    private String outMchid;
}
