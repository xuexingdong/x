package cool.xxd.product.land.infra.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.repository.IdiomRepository;
import cool.xxd.product.land.infra.converter.IdiomConverter;
import cool.xxd.product.land.infra.mapper.IdiomMapper;
import cool.xxd.product.land.infra.model.IdiomDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdiomRepositoryImpl implements IdiomRepository {

    private final IdiomMapper idiomMapper;

    @Override
    public void save(Idiom idiom) {
        var idiomDO = IdiomConverter.INSTANCE.toTarget(idiom);
        idiomMapper.insert(idiomDO);
    }

    @Override
    public void update(Idiom idiom) {
        var idiomDO = IdiomConverter.INSTANCE.toTarget(idiom);
        idiomMapper.updateById(idiomDO);
    }

    @Override
    public Optional<Idiom> findByWord(String word) {
        var queryWrapper = Wrappers.lambdaQuery(IdiomDO.class)
                .eq(IdiomDO::getWord, word);
        var idiomDO = idiomMapper.selectOne(queryWrapper);
        var idiom = IdiomConverter.INSTANCE.toSource(idiomDO);
        return Optional.ofNullable(idiom);
    }

    @Override
    public List<Idiom> findByWords(Collection<String> words) {
        if (words.isEmpty()) {
            return List.of();
        }
        var queryWrapper = Wrappers.lambdaQuery(IdiomDO.class)
                .in(IdiomDO::getWord, words);
        var idiomDOList = idiomMapper.selectList(queryWrapper);
        return IdiomConverter.INSTANCE.toSourceList(idiomDOList);
    }
}
