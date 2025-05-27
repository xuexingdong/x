package cool.xxd.service.pay.application.service.impl;

import group.hckj.pay.application.service.MerchantService;
import cool.xxd.service.pay.domain.command.AddMerchantCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantDomainService merchantDomainService;

    @Override
    public String addMerchant(AddMerchantCommand addMerchantCommand) {
        // TODO 并发
        return merchantDomainService.addMerchant(addMerchantCommand);
    }
}
