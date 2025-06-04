package cool.xxd.product.land.domain.service.impl;

import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.enums.IndexStatus;
import cool.xxd.product.land.domain.repository.QuestionRepository;
import cool.xxd.product.land.domain.service.QuestionDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionDomainServiceImpl implements QuestionDomainService {

    private final QuestionRepository questionRepository;

    @Override
    public void startIndex(List<Question> questions) {
        questions.forEach(Question::startIndex);
        var questionIds = questions.stream().map(Question::getId).toList();
        questionRepository.batchUpdateIndexStatus(questionIds, IndexStatus.UN_INDEXED, IndexStatus.INDEXING);
    }

    @Transactional
    @Override
    public void finishIndex(List<Question> questions) {
        questions.forEach(Question::finishIndex);
        var questionIds = questions.stream().map(Question::getId).toList();
        questionRepository.batchUpdateIndexStatus(questionIds, IndexStatus.INDEXING, IndexStatus.INDEXED);
    }
}
