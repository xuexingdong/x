package cool.xxd.service.trade.domain.valueobject;

import lombok.Data;

@Data
public class OrderOwner {
    private String ownerType;
    private String ownerId;
}
