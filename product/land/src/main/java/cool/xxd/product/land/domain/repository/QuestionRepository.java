package cool.xxd.product.land.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.infra.page.PageRequest;
import cool.xxd.infra.page.PageResult;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.enums.IndexStatus;
import cool.xxd.product.land.domain.query.QuestionQuery;

import java.util.Collection;
import java.util.List;

public interface QuestionRepository extends BaseRepository<Question, Long> {
    void saveAll(List<Question> questions);

    void batchUpdateIndexStatus(Collection<Long> questionIds, IndexStatus fromIndexStatus, IndexStatus toIndexStatus);

    List<Question> queryQuestions(QuestionQuery questionQuery);

    PageResult<Question> queryQuestions(QuestionQuery questionQuery, PageRequest pageRequest);
}
