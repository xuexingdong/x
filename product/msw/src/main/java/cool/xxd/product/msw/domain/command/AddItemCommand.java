package cool.xxd.product.msw.domain.command;

import lombok.Data;

@Data
public class AddItemCommand {
    private String code;
    private String name;
}
