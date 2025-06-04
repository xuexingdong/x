package cool.xxd.product.land.infra.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.domain.factory.IdiomFactory;
import cool.xxd.product.land.infra.model.IdiomDO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class IdiomFactoryImpl implements IdiomFactory {

    @Override
    public Idiom create(AddIdiomCommand addIdiomCommand) {
        Objects.requireNonNull(addIdiomCommand.getWord());
        // 50k 新加坡
        var id = X.id.nextId(IdiomDO.class);
        var idiom = new Idiom();
        idiom.setId(id);
        idiom.setWord(addIdiomCommand.getWord());
        idiom.setPinyin(addIdiomCommand.getPinyin());
        idiom.setEmotion(addIdiomCommand.getEmotion());
        idiom.setSynonyms(addIdiomCommand.getSynonyms());
        idiom.setAntonyms(addIdiomCommand.getAntonyms());
        idiom.setMeaning(addIdiomCommand.getMeaning());
        idiom.setOrigin(addIdiomCommand.getOrigin());
        idiom.setUsage(addIdiomCommand.getUsage());
        idiom.setExample(addIdiomCommand.getExample());
        idiom.setDistinction(addIdiomCommand.getDistinction());
        idiom.setStory(addIdiomCommand.getStory());
        idiom.setExplanation(addIdiomCommand.getExplanation());
        idiom.setExampleSentences(addIdiomCommand.getExampleSentences());
        idiom.setLastUpdateTime(LocalDateTime.now());
        return idiom;
    }
}
