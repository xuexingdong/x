package cool.xxd.product.land.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.land.domain.aggregate.Paper;

import java.util.List;
import java.util.Optional;

public interface PaperRepository extends BaseRepository<Paper, Long> {

    Optional<Paper> findByOutPaperId(String outPaperId);

    List<Paper> findByPaperIds(List<Long> paperIds);
}
