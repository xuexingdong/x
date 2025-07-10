package cool.xxd.service.pay.domain.command;

import lombok.Data;

@Data
public abstract class BaseCommand {
    // TODO 鉴权机制
    private String mchid;
    private String appid;
}
