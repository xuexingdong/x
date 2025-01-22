package cool.xxd.product.land.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("land_paper")
public class PaperDO extends BaseDO {
    private String outPaperId;
    private String name;
    private Integer questionCount;
}
