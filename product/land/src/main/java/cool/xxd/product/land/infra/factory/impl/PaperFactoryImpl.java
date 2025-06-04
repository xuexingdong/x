package cool.xxd.product.land.infra.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.domain.factory.PaperFactory;
import cool.xxd.product.land.infra.model.PaperDO;
import org.springframework.stereotype.Component;

@Component
public class PaperFactoryImpl implements PaperFactory {
    @Override
    public Paper create(AddPaperCommand addPaperCommand) {
        var id = X.id.nextId(PaperDO.class);
        var paper = new Paper();
        paper.setId(id);
        paper.setOutPaperId(addPaperCommand.getOutPaperId());
        paper.setName(addPaperCommand.getName());
        paper.setQuestionCount(addPaperCommand.getQuestionCount());
        return paper;
    }
}
