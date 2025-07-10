package cool.xxd.service.pay.domain.strategy.fuiou;

import lombok.Data;

@Data
public class FuiouPayChannelConfig {
    private String mchnt_cd;
    private String term_id;
    private String ins_cd;
}
