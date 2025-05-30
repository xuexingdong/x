package cool.xxd.product.land.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("land_idiom_question_relation")
public class IdiomQuestionRelationDO extends XBaseDO {
    private String idiom;
    private Long questionId;
}
