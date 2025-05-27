package cool.xxd.service.pay.domain.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CloseCommand extends BaseCommand {
    private String outTradeNo;

    private String payOrderNo;
}
