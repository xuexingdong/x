package cool.xxd.product.msw.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.query.MobQuery;

import java.util.List;

public interface MobRepository extends BaseRepository<Mob, Long> {

    void saveAll(List<Mob> mobs);

    List<Mob> query(MobQuery mobQuery);

    List<Mob> findByCodes(List<String> mobCodes);

    List<Mob> findByNames(List<String> mobNames);
}
