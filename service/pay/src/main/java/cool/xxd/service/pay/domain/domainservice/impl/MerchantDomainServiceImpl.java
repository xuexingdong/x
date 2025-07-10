package cool.xxd.service.pay.domain.domainservice.impl;

import cn.hutool.core.util.HashUtil;
import cool.xxd.service.pay.domain.command.ApplyMerchantCommand;
import cool.xxd.service.pay.domain.command.ApproveMerchantApplyCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantDomainService;
import cool.xxd.service.pay.domain.enums.MerchantApplyStatusEnum;
import cool.xxd.service.pay.domain.exceptions.PayException;
import cool.xxd.service.pay.domain.factory.MerchantApplyFactory;
import cool.xxd.service.pay.domain.factory.MerchantFactory;
import cool.xxd.service.pay.domain.repository.MerchantApplyRepository;
import cool.xxd.service.pay.domain.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class MerchantDomainServiceImpl implements MerchantDomainService {

    private final MerchantApplyFactory merchantApplyFactory;
    private final MerchantApplyRepository merchantApplyRepository;
    private final MerchantFactory merchantFactory;
    private final MerchantRepository merchantRepository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public String apply(ApplyMerchantCommand applyMerchantCommand) {
        validateApply(applyMerchantCommand);
        var merchantApply = merchantApplyFactory.create(applyMerchantCommand);
        merchantApplyRepository.save(merchantApply);
        return merchantApply.getMerchantApplyNo();
    }

    private void validateApply(ApplyMerchantCommand applyMerchantCommand) {

    }

    @Override
    public String approveApply(ApproveMerchantApplyCommand approveMerchantApplyCommand) {
        var merchantApply = merchantApplyRepository.findByMerchantApplyNo(approveMerchantApplyCommand.getMerchantApplyNo())
                .orElseThrow(() -> new PayException("商户申请单不存在"));
        merchantApply.approve(approveMerchantApplyCommand.getOperatorId());
        var mchid = generateMchid();
        merchantRepository.findByMchid(mchid)
                .ifPresent(merchant -> {
                    throw new PayException("商户号生成重复，请重新添加");
                });
        var merchant = merchantFactory.create(merchantApply, mchid);
        return transactionTemplate.execute(transactionStatus -> {
            var updated = merchantApplyRepository.updateApplyStatus(merchantApply, MerchantApplyStatusEnum.PENDING);
            if (!updated) {
                throw new PayException("更新商户申请状态失败");
            }
            merchantRepository.save(merchant);
            return merchant.getMchid();
        });
    }

    private String generateMchid() {
        var uniqueStr = String.valueOf(System.currentTimeMillis());
        return String.valueOf(HashUtil.fnvHash(uniqueStr));
    }
}

