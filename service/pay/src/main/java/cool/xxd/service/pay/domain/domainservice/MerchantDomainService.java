package cool.xxd.service.pay.domain.domainservice;

import cool.xxd.service.pay.domain.command.AddMerchantCommand;

public interface MerchantDomainService {
    String addMerchant(AddMerchantCommand addMerchantCommand);
}
