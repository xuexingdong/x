package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.service.pay.application.model.MerchantDO;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import cool.xxd.service.pay.domain.aggregate.MerchantApply;
import cool.xxd.service.pay.domain.command.AddMerchantCommand;
import cool.xxd.service.pay.domain.factory.MerchantFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantFactoryImpl implements MerchantFactory {

    @Override
    public Merchant create(MerchantApply merchantApply, String mchid) {
        var merchant = new Merchant();
        merchant.setId(X.id.nextId(MerchantDO.class));
        merchant.setMchid(mchid);
        merchant.setName(merchantApply.getMerchantName());
        merchant.setMerchantApplyNo(merchantApply.getMerchantApplyNo());
        return merchant;
    }
}
