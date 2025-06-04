package cool.xxd.product.land.domain.service.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.application.constant.Constants;
import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.domain.factory.IdiomFactory;
import cool.xxd.product.land.domain.factory.IdiomQuestionRelationFactory;
import cool.xxd.product.land.domain.repository.IdiomQuestionRelationRepository;
import cool.xxd.product.land.domain.repository.IdiomRepository;
import cool.xxd.product.land.domain.service.IdiomDomainService;
import cool.xxd.product.land.domain.valueobject.IdiomCrawlItem;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdiomDomainServiceImpl implements IdiomDomainService {

    private final IdiomRepository idiomRepository;
    private final IdiomFactory idiomFactory;
    private final IdiomQuestionRelationRepository idiomQuestionRelationRepository;
    private final IdiomQuestionRelationFactory idiomQuestionRelationFactory;

    @Transactional
    @Override
    public void addIdiom(AddIdiomCommand command) {
        idiomRepository.findByWord(command.getWord())
                .ifPresentOrElse(idiom -> {
                    idiom.update(command);
                    idiomRepository.update(idiom);
                }, () -> {
                    var idiom = idiomFactory.create(command);
                    idiomRepository.save(idiom);
                });
        if (command.getQuestionIds() != null) {
            addQuestionRelations(command.getWord(), command.getQuestionIds());
        }
    }

    @Override
    public void addQuestionRelations(String word, List<Long> questionIds) {
        if (questionIds == null || questionIds.isEmpty()) {
            return;
        }
        var idiomQuestionRelations = idiomQuestionRelationRepository.findByIdiom(word);
        var existQuestionIdSet = idiomQuestionRelations.stream().map(IdiomQuestionRelation::getQuestionId).collect(Collectors.toSet());
        var difference = new HashSet<>(questionIds);
        difference.removeAll(existQuestionIdSet);
        if (difference.isEmpty()) {
            return;
        }
        var idiomQuestionRelation = idiomQuestionRelationFactory.create(word, difference);
        idiomQuestionRelationRepository.saveAll(idiomQuestionRelation);
    }

    @Override
    public void crawl(IdiomCrawlItem item) {
        X.cache.addFirst(Constants.CRAWLER_REDIS_KEY, item);
    }
}
