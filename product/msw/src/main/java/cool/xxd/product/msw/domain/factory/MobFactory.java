package cool.xxd.product.msw.domain.factory;

import cool.xxd.product.msw.domain.aggregate.Mob;
import cool.xxd.product.msw.domain.command.AddMobCommand;

import java.util.List;

public interface MobFactory {
    List<Mob> createMob(List<AddMobCommand> addMobCommands);
}
