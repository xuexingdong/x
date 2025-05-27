package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mapper.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_app")
public class AppDO extends BaseDO {
    private String appid;
    // 订单号前缀
    private String orderNoPrefix;
    private String notifyUrl;
    private String publicKey;
    private String privateKey;
    private String config;
}
