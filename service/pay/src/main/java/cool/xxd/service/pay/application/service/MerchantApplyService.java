package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;
import cool.xxd.service.pay.domain.command.ApproveMerchantApplyCommand;

public interface MerchantApplyService {
    String apply(ApplyMerchantCommand applyMerchantCommand);

    String approveApply(ApproveMerchantApplyCommand approveMerchantApplyCommand);
}
