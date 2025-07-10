package cool.xxd.service.pay.application.service.impl;

import cool.xxd.service.pay.application.service.MerchantApplyService;
import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;
import cool.xxd.service.pay.domain.command.ApproveMerchantApplyCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantApplyServiceImpl implements MerchantApplyService {

    private final MerchantDomainService merchantDomainService;

    @Override
    public String apply(ApplyMerchantCommand applyMerchantCommand) {
        return merchantDomainService.apply(applyMerchantCommand);
    }

    @Override
    public String approveApply(ApproveMerchantApplyCommand approveMerchantApplyCommand) {
        return merchantDomainService.approveApply(approveMerchantApplyCommand);
    }
}
