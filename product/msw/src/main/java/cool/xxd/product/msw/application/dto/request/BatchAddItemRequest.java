package cool.xxd.product.msw.application.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchAddItemRequest {
    private List<AddItemRequest> items;
}
