package cool.xxd.service.pay.domain.aggregate;

import lombok.Data;

@Data
public class OrderLog {
    private Long id;
    private String appid;
    private String mchid;
    private String payOrderNo;
    private String refundOrderNo;
    private String req;
    private String resp;

    public void updateResp(String resp) {
        this.resp = resp;
    }
}
