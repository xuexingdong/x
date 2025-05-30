package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.service.pay.application.model.MerchantDO;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.command.AddMerchantCommand;
import cool.xxd.service.pay.domain.factory.MerchantFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantFactoryImpl implements MerchantFactory {
    private final IdGenerator idGenerator;

    @Override
    public Merchant create(AddMerchantCommand addMerchantCommand, String mchid) {
        var merchant = new Merchant();
        merchant.setId(idGenerator.nextId(MerchantDO.class));
        merchant.setAppid(addMerchantCommand.getAppid());
        merchant.setMchid(mchid);
        merchant.setName(addMerchantCommand.getName());
        merchant.setOutMchid(addMerchantCommand.getOutMchid());
        return merchant;
    }
}
