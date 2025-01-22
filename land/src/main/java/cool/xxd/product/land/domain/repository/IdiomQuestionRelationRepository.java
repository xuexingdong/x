package cool.xxd.product.land.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;

import java.util.List;
import java.util.Optional;

public interface IdiomQuestionRelationRepository extends BaseRepository<IdiomQuestionRelation, Long> {

    void saveAll(List<IdiomQuestionRelation> idiomQuestionRelations);

    List<IdiomQuestionRelation> findByIdiom(String idiom);

    Optional<IdiomQuestionRelation> findByIdiomAndQuestionId(String idiom, Long questionId);

}
