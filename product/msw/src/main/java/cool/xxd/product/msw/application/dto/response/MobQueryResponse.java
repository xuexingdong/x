package cool.xxd.product.msw.application.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MobQueryResponse extends MobResponse{
    private List<ItemResponse> items;
}
