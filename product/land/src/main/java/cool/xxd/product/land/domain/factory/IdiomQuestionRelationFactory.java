package cool.xxd.product.land.domain.factory;

import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;

import java.util.Collection;
import java.util.List;

public interface IdiomQuestionRelationFactory {
    IdiomQuestionRelation create(String idiom, Long questionId);

    List<IdiomQuestionRelation> create(String idiom, Collection<Long> questionId);
}
