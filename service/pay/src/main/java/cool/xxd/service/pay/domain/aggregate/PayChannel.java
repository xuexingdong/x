package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class PayChannel {
    private Long id;
    private String code;
    private String name;
}
