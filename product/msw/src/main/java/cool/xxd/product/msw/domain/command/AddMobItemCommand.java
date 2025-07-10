package cool.xxd.product.msw.domain.command;

import lombok.Data;

import java.util.List;

@Data
public class AddMobItemCommand {
    private String mobName;
    private List<String> itemName;
}
