package cool.xxd.service.pay.application.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hckj_pc_order_log")
public class OrderLogDO extends XBaseDO {
    private String appid;
    private String mchid;
    private String payOrderNo;
    private String refundOrderNo;
    private String req;
    private String resp;
}
