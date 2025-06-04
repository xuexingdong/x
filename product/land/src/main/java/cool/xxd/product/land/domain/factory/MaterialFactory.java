package cool.xxd.product.land.domain.factory;

import cool.xxd.product.land.domain.aggregate.Material;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.command.AddPaperCommand;

import java.util.List;

public interface MaterialFactory {
    List<Material> create(Paper paper, AddPaperCommand addPaperCommand);
}
