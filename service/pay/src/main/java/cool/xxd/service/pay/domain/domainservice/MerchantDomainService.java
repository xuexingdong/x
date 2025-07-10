package cool.xxd.service.pay.domain.domainservice;

import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;
import cool.xxd.service.pay.domain.command.ApproveMerchantApplyCommand;

public interface MerchantDomainService {

    String apply(ApplyMerchantCommand applyMerchantCommand);

    String approveApply(ApproveMerchantApplyCommand approveMerchantApplyCommand);

}
