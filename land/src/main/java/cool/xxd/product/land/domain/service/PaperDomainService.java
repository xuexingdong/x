package cool.xxd.product.land.domain.service;

import cool.xxd.product.land.domain.command.AddPaperCommand;

public interface PaperDomainService {
    void addPaper(AddPaperCommand addPaperCommand);
}
