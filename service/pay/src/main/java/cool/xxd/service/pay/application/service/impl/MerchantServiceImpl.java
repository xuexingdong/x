package cool.xxd.service.pay.application.service.impl;

import cool.xxd.service.pay.application.service.MerchantService;
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
        return merchantDomainService.addMerchant(addMerchantCommand);
    }
}
