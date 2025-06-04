package cool.xxd.product.land.domain.service;

import cool.xxd.product.land.domain.aggregate.Question;

import java.util.List;

public interface QuestionDomainService {
    void startIndex(List<Question> questions);

    void finishIndex(List<Question> questions);
}
