package cool.xxd.product.msw.domain.aggregate;

import lombok.Data;

@Data
public class MobItem {
    private Long id;
    private String mobCode;
    private String itemCode;
    private Integer quantity;
}
