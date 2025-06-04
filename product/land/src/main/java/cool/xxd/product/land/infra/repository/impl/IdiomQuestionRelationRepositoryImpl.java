package cool.xxd.product.land.infra.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.land.domain.repository.IdiomQuestionRelationRepository;
import cool.xxd.product.land.domain.valueobject.IdiomQuestionRelation;
import cool.xxd.product.land.infra.converter.IdiomQuestionRelationConverter;
import cool.xxd.product.land.infra.mapper.IdiomQuestionRelationMapper;
import cool.xxd.product.land.infra.model.IdiomQuestionRelationDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdiomQuestionRelationRepositoryImpl implements IdiomQuestionRelationRepository {

    private final IdiomQuestionRelationMapper idiomQuestionRelationMapper;

    @Override
    public void save(IdiomQuestionRelation idiomQuestionRelation) {
        var idiomQuestionRelationDO = IdiomQuestionRelationConverter.INSTANCE.toTarget(idiomQuestionRelation);
        idiomQuestionRelationMapper.insert(idiomQuestionRelationDO);
    }

    @Override
    public void saveAll(List<IdiomQuestionRelation> idiomQuestionRelations) {
        if (idiomQuestionRelations.isEmpty()) {
            return;
        }
        var idiomQuestionRelationDOList = IdiomQuestionRelationConverter.INSTANCE.toTargetList(idiomQuestionRelations);
        idiomQuestionRelationMapper.insert(idiomQuestionRelationDOList);
    }

    @Override
    public List<IdiomQuestionRelation> findByIdiom(String idiom) {
        var queryWrapper = Wrappers.lambdaQuery(IdiomQuestionRelationDO.class)
                .eq(IdiomQuestionRelationDO::getIdiom, idiom);
        var idiomQuestionRelationDOList = idiomQuestionRelationMapper.selectList(queryWrapper);
        return IdiomQuestionRelationConverter.INSTANCE.toSourceList(idiomQuestionRelationDOList);
    }

    @Override
    public Optional<IdiomQuestionRelation> findByIdiomAndQuestionId(String idiom, Long questionId) {
        var queryWrapper = Wrappers.lambdaQuery(IdiomQuestionRelationDO.class)
                .eq(IdiomQuestionRelationDO::getIdiom, idiom)
                .eq(IdiomQuestionRelationDO::getQuestionId, questionId);
        var idiomQuestionRelationDO = idiomQuestionRelationMapper.selectOne(queryWrapper);
        var idiomQuestionRelation = IdiomQuestionRelationConverter.INSTANCE.toSource(idiomQuestionRelationDO);
        return Optional.ofNullable(idiomQuestionRelation);
    }
}
