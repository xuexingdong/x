package cool.xxd.service.pay.application.service;

import cool.xxd.service.pay.domain.command.AddMerchantCommand;

public interface MerchantService {
    String addMerchant(AddMerchantCommand addMerchantCommand);
}
