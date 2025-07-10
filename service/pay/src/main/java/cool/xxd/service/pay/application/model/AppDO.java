package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("pay_app")
public class AppDO extends XBaseDO {
    private String mchid;
    private String appid;
    // 订单号前缀
    private String orderNoPrefix;
    private String notifyUrl;
    private String publicKey;
    private String privateKey;
    private String config;
}
