package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class Merchant {
    private Long id;
    private String mchid;
    private String name;
    private String merchantApplyNo;
}
