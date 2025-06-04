package cool.xxd.product.land.domain.factory;

import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.command.AddPaperCommand;

public interface PaperFactory {
    Paper create(AddPaperCommand addPaperCommand);
}
