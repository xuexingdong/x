package cool.xxd.product.msw.application.dto.request;

import lombok.Data;

@Data
public class AddItemRequest {
    private String code;
    private String name;
    private String itemType;
    private String itemTypeName;
}
