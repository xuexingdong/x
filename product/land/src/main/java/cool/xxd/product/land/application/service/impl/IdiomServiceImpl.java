package cool.xxd.product.land.application.service.impl;

import cool.xxd.product.land.application.service.IdiomService;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.repository.IdiomRepository;
import cool.xxd.product.land.domain.service.IdiomDomainService;
import cool.xxd.product.land.domain.valueobject.IdiomCrawlItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdiomServiceImpl implements IdiomService {

    private final IdiomRepository idiomRepository;
    private final IdiomDomainService idiomDomainService;

    @Override
    public Optional<Idiom> search(String keyword) {
        var idiomOptional = idiomRepository.findByWord(keyword);
        if (idiomOptional.isEmpty()) {
            var idiomCrawlItem = new IdiomCrawlItem();
            idiomCrawlItem.setWord(keyword);
            idiomDomainService.crawl(idiomCrawlItem);
        }
        return idiomOptional;
    }
}
