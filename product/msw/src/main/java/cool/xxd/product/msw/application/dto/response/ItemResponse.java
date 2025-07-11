package cool.xxd.product.msw.application.dto.response;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;
    private String code;
    private String name;
    private String itemType;
    private String itemTypeName;
}
