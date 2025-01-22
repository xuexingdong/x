package cool.xxd.product.land.infra.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.infra.page.PageRequest;
import cool.xxd.infra.page.PageResult;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.enums.IndexStatus;
import cool.xxd.product.land.domain.query.QuestionQuery;
import cool.xxd.product.land.domain.repository.QuestionRepository;
import cool.xxd.product.land.infra.converter.QuestionConverter;
import cool.xxd.product.land.infra.mapper.QuestionMapper;
import cool.xxd.product.land.infra.model.QuestionDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionMapper questionMapper;

    @Override
    public void save(Question question) {
        var questionDO = QuestionConverter.INSTANCE.toTarget(question);
        questionMapper.insert(questionDO);
    }

    public void saveAll(List<Question> questions) {
        if (questions.isEmpty()) {
            return;
        }
        var questionDOList = QuestionConverter.INSTANCE.toTargetList(questions);
        questionMapper.insert(questionDOList);
    }

    @Override
    public void batchUpdateIndexStatus(Collection<Long> questionIds, IndexStatus fromIndexStatus, IndexStatus toIndexStatus) {
        if (questionIds.isEmpty()) {
            return;
        }
        var updateWrapper = Wrappers.lambdaUpdate(QuestionDO.class)
                .in(QuestionDO::getId, questionIds)
                .eq(QuestionDO::getIndexStatus, fromIndexStatus.getCode())
                .set(QuestionDO::getIndexStatus, toIndexStatus.getCode());
        questionMapper.update(updateWrapper);
    }

    @Override
    public void update(Question question) {
        var questionDO = QuestionConverter.INSTANCE.toTarget(question);
        questionMapper.updateById(questionDO);
    }

    @Override
    public List<Question> findByIds(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var questionDOList = questionMapper.selectByIds(ids);
        return QuestionConverter.INSTANCE.toSourceList(questionDOList);
    }

    @Override
    public List<Question> queryQuestions(QuestionQuery questionQuery) {
        var queryWrapper = Wrappers.lambdaQuery(QuestionDO.class);
        if (questionQuery.getIndexStatus() != null) {
            queryWrapper.eq(QuestionDO::getIndexStatus, questionQuery.getIndexStatus().getCode());
        }
        var questionDOList = questionMapper.selectList(queryWrapper);
        return QuestionConverter.INSTANCE.toSourceList(questionDOList);
    }

    @Override
    public PageResult<Question> queryQuestions(QuestionQuery questionQuery, PageRequest pageRequest) {
        var queryWrapper = Wrappers.lambdaQuery(QuestionDO.class);
        queryWrapper.eq(QuestionDO::getIndexStatus, questionQuery.getIndexStatus().getCode());
        var questionDOIPage = questionMapper.selectPage(pageRequest.toIPage(), queryWrapper);
        var memberGrowthValueRecordList = QuestionConverter.INSTANCE.toSourceList(questionDOIPage.getRecords());
        return PageResult.of(memberGrowthValueRecordList, questionDOIPage.getTotal());
    }
}
