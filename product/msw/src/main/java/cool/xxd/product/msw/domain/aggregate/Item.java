package cool.xxd.product.msw.domain.aggregate;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String code;
    private String name;
    private Integer itemType;
    private String itemTypeName;
}
