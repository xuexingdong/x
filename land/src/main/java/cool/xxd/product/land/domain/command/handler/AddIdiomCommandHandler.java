package cool.xxd.product.land.domain.command.handler;

import cool.xxd.infra.ddd.CommandHandlerWithoutResult;
import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.domain.service.IdiomDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddIdiomCommandHandler implements CommandHandlerWithoutResult<AddIdiomCommand> {

    private final IdiomDomainService idiomDomainService;

    @Override
    public void handle(AddIdiomCommand command) {
        // TODO lock
        idiomDomainService.addIdiom(command);
    }
}
