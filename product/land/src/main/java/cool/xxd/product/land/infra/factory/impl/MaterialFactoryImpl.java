package cool.xxd.product.land.infra.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.domain.aggregate.Material;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.domain.factory.MaterialFactory;
import cool.xxd.product.land.infra.model.MaterialDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialFactoryImpl implements MaterialFactory {

    @Override
    public List<Material> create(Paper paper, AddPaperCommand addPaperCommand) {
        if (addPaperCommand.getMaterials() == null) {
            return List.of();
        }
        return addPaperCommand.getMaterials().stream().map(addMaterialCommand -> {
            var id = X.id.nextId(MaterialDO.class);
            var material = new Material();
            material.setId(id);
            material.setOutMaterialId(addMaterialCommand.getOutMaterialId());
            material.setContent(addMaterialCommand.getContent());
            material.setPaperId(paper.getId());
            return material;
        }).toList();
    }
}
