package cool.xxd.product.land.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.land.domain.aggregate.Material;

import java.util.List;

public interface MaterialRepository extends BaseRepository<Material, Long> {

    void saveAll(List<Material> materials);
}
