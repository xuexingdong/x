package cool.xxd.product.msw.application.factory;

import cool.xxd.infra.X;
import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.factory.MobFactory;
import cool.xxd.product.msw.infra.model.MobDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MobFactoryImpl implements MobFactory {
    @Override
    public List<Mob> createMob(List<AddMobCommand> addMobCommands) {
        return addMobCommands.stream()
                .map(addMobCommand -> {
                    var id = X.id.nextId(MobDO.class);
                    var mob = new Mob();
                    mob.setId(id);
                    mob.setCode(addMobCommand.getCode());
                    mob.setName(addMobCommand.getName());
                    mob.setLevel(addMobCommand.getLevel());
                    mob.setMaxHp(addMobCommand.getMaxHp());
                    mob.setMaxMp(addMobCommand.getMaxMp());
                    mob.setExp(addMobCommand.getExp());
                    mob.setPhysicalDefense(addMobCommand.getPhysicalDefense());
                    mob.setMagicalDefense(addMobCommand.getMagicalDefense());
                    mob.setEvasion(addMobCommand.getEvasion());
                    mob.setBaseAccuracyRequirement(addMobCommand.getBaseAccuracyRequirement());
                    mob.setAccuracyLevelPenalty(addMobCommand.getAccuracyLevelPenalty());
                    return mob;
                }).toList();
    }
}
