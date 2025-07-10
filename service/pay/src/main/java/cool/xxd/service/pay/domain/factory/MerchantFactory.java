package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.aggregate.MerchantApply;
import cool.xxd.service.pay.domain.command.AddMerchantCommand;

public interface MerchantFactory {
    Merchant create(MerchantApply merchantApply, String mchid);
}
