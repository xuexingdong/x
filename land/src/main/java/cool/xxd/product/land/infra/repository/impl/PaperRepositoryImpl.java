package cool.xxd.product.land.infra.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.repository.PaperRepository;
import cool.xxd.product.land.infra.converter.PaperConverter;
import cool.xxd.product.land.infra.mapper.PaperMapper;
import cool.xxd.product.land.infra.model.PaperDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaperRepositoryImpl implements PaperRepository {

    private final PaperMapper paperMapper;

    @Override
    public void save(Paper paper) {
        var questionDO = PaperConverter.INSTANCE.toTarget(paper);
        paperMapper.insert(questionDO);
    }

    @Override
    public Optional<Paper> findById(Long id) {
        var paperDO = paperMapper.selectById(id);
        var paper = PaperConverter.INSTANCE.toSource(paperDO);
        return Optional.ofNullable(paper);
    }

    @Override
    public Optional<Paper> findByOutPaperId(String outPaperId) {
        var queryWrapper = Wrappers.lambdaQuery(PaperDO.class)
                .eq(PaperDO::getOutPaperId, outPaperId);
        var paperDO = paperMapper.selectOne(queryWrapper);
        var paper = PaperConverter.INSTANCE.toSource(paperDO);
        return Optional.ofNullable(paper);
    }

    @Override
    public List<Paper> findByPaperIds(List<Long> paperIds) {
        if (paperIds.isEmpty()) {
            return List.of();
        }
        var paperDO = paperMapper.selectByIds(paperIds);
        return PaperConverter.INSTANCE.toSourceList(paperDO);
    }
}
