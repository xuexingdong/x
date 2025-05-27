package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.App;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.enums.PayChannelEnum;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;

public interface PayOrderFactory {
    PayOrder create(App app, Merchant merchant, PayCommand payCommand, PayTypeEnum payType, PayChannelEnum payChannel);
}
