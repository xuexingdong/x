package cool.xxd.product.land.ui.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdiomCardVO extends IdiomVO {
    // 关联真题数
    private Integer relatedQuestionCount;
}
