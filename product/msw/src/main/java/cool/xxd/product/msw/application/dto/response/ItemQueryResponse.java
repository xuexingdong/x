package cool.xxd.product.msw.application.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemQueryResponse extends ItemResponse {
    private List<MobResponse> mobs;
}
