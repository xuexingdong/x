package cool.xxd.product.land.domain.service;

import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.domain.valueobject.IdiomCrawlItem;

import java.util.List;

public interface IdiomDomainService {
    void addIdiom(AddIdiomCommand command);

    void addQuestionRelations(String word, List<Long> questionIds);

    void crawl(IdiomCrawlItem item);
}
