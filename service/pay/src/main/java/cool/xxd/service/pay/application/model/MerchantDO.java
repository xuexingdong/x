package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("pay_merchant")
public class MerchantDO extends XBaseDO {
    private String appid;
    private String mchid;
    private String name;
}
