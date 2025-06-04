package cool.xxd.product.msw.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("msw_mob_item")
public class MobItemDO extends XBaseDO {
    private String mobCode;
    private String itemCode;
}
