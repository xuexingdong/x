package cool.xxd.product.land.domain.valueobject;

import lombok.Data;

@Data
public class IdiomQuestionRelation {
    private Long id;
    private String idiom;
    private Long questionId;
}
