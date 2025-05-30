package cool.xxd.product.land.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("land_material")
public class MaterialDO extends XBaseDO {
    private Long id;
    private String outMaterialId;
    private String content;
    private Long paperId;
}
