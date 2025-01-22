package cool.xxd.product.land.domain.factory;

import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.command.AddIdiomCommand;

public interface IdiomFactory {
    Idiom create(AddIdiomCommand addIdiomCommand);
}
