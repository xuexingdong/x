package cool.xxd.product.land.domain.command.handler;

import cool.xxd.infra.ddd.CommandHandlerWithoutResult;
import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.domain.service.PaperDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddPaperCommandHandler implements CommandHandlerWithoutResult<AddPaperCommand> {

    private final PaperDomainService paperDomainService;

    @Override
    public void handle(AddPaperCommand command) {
        // TODO lock
        paperDomainService.addPaper(command);
    }
}
