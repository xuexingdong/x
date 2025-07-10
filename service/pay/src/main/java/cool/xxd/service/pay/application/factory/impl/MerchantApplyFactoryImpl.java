package cool.xxd.service.pay.application.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.service.pay.application.model.MerchantApplyDO;
import cool.xxd.service.pay.domain.aggregate.MerchantApply;
import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;
import cool.xxd.service.pay.domain.constants.CacheKeys;
import cool.xxd.service.pay.domain.enums.MerchantApplyStatusEnum;
import cool.xxd.service.pay.domain.factory.MerchantApplyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantApplyFactoryImpl implements MerchantApplyFactory {

    @Override
    public MerchantApply create(ApplyMerchantCommand applyMerchantCommand) {
        var merchantApplyNo = X.serial.generate(CacheKeys.MERCHANT_APPLY_SERIAL_NO);
        var merchantApply = new MerchantApply();
        merchantApply.setId(X.id.nextId(MerchantApplyDO.class));
        merchantApply.setMerchantApplyNo(merchantApplyNo);
        merchantApply.setMerchantName(applyMerchantCommand.getMerchantName());
        merchantApply.setApplyStatus(MerchantApplyStatusEnum.PENDING);
        merchantApply.setApplyData(applyMerchantCommand.getApplyData());
        merchantApply.setApplyUserId(applyMerchantCommand.getUserId());
        return merchantApply;
    }
}
