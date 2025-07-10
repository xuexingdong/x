package cool.xxd.service.pay.domain.factory;

import cool.xxd.service.pay.domain.aggregate.MerchantApply;
import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;

public interface MerchantApplyFactory {
    MerchantApply create(ApplyMerchantCommand applyMerchantCommand);
}
