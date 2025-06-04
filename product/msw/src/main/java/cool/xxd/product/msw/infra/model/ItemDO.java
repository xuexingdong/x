package cool.xxd.product.msw.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "msw_item")
public class ItemDO extends XBaseDO {
    private String code;
    private String name;
}
