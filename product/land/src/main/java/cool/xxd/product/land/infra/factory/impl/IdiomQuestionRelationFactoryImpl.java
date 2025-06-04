package cool.xxd.product.land.infra.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.domain.factory.IdiomQuestionRelationFactory;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;
import cool.xxd.product.land.infra.model.IdiomQuestionRelationDO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class IdiomQuestionRelationFactoryImpl implements IdiomQuestionRelationFactory {

    @Override
    public IdiomQuestionRelation create(String idiom, Long questionId) {
        var id = X.id.nextId(IdiomQuestionRelationDO.class);
        var idiomQuestionRelation = new IdiomQuestionRelation();
        idiomQuestionRelation.setId(id);
        idiomQuestionRelation.setIdiom(idiom);
        idiomQuestionRelation.setQuestionId(questionId);
        return idiomQuestionRelation;
    }

    @Override
    public List<IdiomQuestionRelation> create(String idiom, Collection<Long> questionIds) {
        return questionIds.stream().map(questionId -> create(idiom, questionId)).toList();
    }
}
