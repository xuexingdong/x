package cool.xxd.product.land.application.service.impl;

import cool.xxd.infra.page.PageRequest;
import cool.xxd.product.land.application.service.QuestionService;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.enums.IndexStatus;
import cool.xxd.product.land.domain.enums.XingceModule;
import cool.xxd.product.land.domain.query.QuestionQuery;
import cool.xxd.product.land.domain.repository.IdiomQuestionRelationRepository;
import cool.xxd.product.land.domain.repository.IdiomRepository;
import cool.xxd.product.land.domain.repository.QuestionRepository;
import cool.xxd.product.land.domain.service.IdiomDomainService;
import cool.xxd.product.land.domain.service.QuestionDomainService;
import cool.xxd.product.land.domain.valueobject.IdiomCrawlItem;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final String SPLIT_REGEX = "[,、\\s]+";

    private final QuestionRepository questionRepository;
    private final IdiomQuestionRelationRepository idiomQuestionRelationRepository;
    private final QuestionDomainService questionDomainService;
    private final IdiomDomainService idiomDomainService;
    private final IdiomRepository idiomRepository;

    @Override
    public List<Question> search(String idiom) {
        var idiomQuestionRelations = idiomQuestionRelationRepository.findByIdiom(idiom);
        if (idiomQuestionRelations.isEmpty()) {
            return List.of();
        }
        var questionIds = idiomQuestionRelations.stream()
                .map(IdiomQuestionRelation::getQuestionId).toList();
        return questionRepository.findByIds(questionIds);
    }

    @Override
    public void indexIdioms() {
        var pageRequest = PageRequest.of(1, 500);
        var questionQuery = new QuestionQuery();
        questionQuery.setIndexStatus(IndexStatus.UN_INDEXED);
        var questions = questionRepository.queryQuestions(questionQuery, pageRequest).getData();
        if (questions.isEmpty()) {
            return;
        }
        questionDomainService.startIndex(questions);

        var questionMap = questions.stream()
                .collect(Collectors.partitioningBy(question -> question.getModule() == XingceModule.YANYU));
        // 从分组中获取需要索引和不需要索引的题目
        var noNeedIndexQuestions = questionMap.get(false);
        questionDomainService.finishIndex(noNeedIndexQuestions);

        var needIndexQuestions = questionMap.get(true);
        var idiomIndexMap = new HashMap<String, List<Long>>();
        for (var needIndexQuestion : needIndexQuestions) {
            var idioms = needIndexQuestion.getOptions().stream()
                    .flatMap(option -> Arrays.stream(option.split(SPLIT_REGEX)))
                    .map(String::trim)  // trim 成语
                    .filter(this::matchIdiom)  // 过滤符合条件的成语
                    .distinct()  // 如果成语有重复，去重
                    .toList();

            // 加入到倒排索引中
            for (var idiom : idioms) {
                // 使用 computeIfAbsent，避免两次查找
                idiomIndexMap.computeIfAbsent(idiom, _ -> new ArrayList<>()).add(needIndexQuestion.getId());
            }
        }
        var words = idiomIndexMap.keySet();
        var existIdiomSets = idiomRepository.findByWords(words).stream()
                .map(Idiom::getWord).collect(Collectors.toSet());
        for (var word : words) {
            // 对于已经收录的，可以确定是成语，追加题目关系
            if (existIdiomSets.contains(word)) {
                log.info("已经收录的成语-{}", word);
                idiomDomainService.addQuestionRelations(word, idiomIndexMap.get(word));
            }
            // 其余的去加入爬取任务
            else {
                log.info("爬取的成语-{}", word);
                var items = idiomIndexMap.get(word).stream().map(questionId -> {
                    var idiomCrawlItem = new IdiomCrawlItem();
                    idiomCrawlItem.setWord(word);
                    idiomCrawlItem.setQuestionId(questionId);
                    return idiomCrawlItem;
                }).toList();
                idiomDomainService.crawl(items);
            }
        }
        questionDomainService.finishIndex(needIndexQuestions);
    }

    @Override
    public Integer countByIdiom(Idiom idiom) {
        return idiomQuestionRelationRepository.findByIdiom(idiom.getWord()).size();
    }

    private boolean matchIdiom(String s) {
        return s.length() == 4 && s.matches("^[\\u4e00-\\u9fa5]+$");
    }
}
