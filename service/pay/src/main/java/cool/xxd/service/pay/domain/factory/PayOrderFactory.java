package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.*;
import cool.xxd.service.pay.domain.command.PayCommand;
import cool.xxd.service.pay.domain.enums.PayTypeEnum;

public interface PayOrderFactory {
    PayOrder create(Merchant merchant,
                    App app,
                    PayTypeEnum payType,
                    PayChannel payChannel,
                    MerchantPayChannel merchantPayChannel,
                    PayCommand payCommand
    );
}
