package cool.xxd.service.pay.domain.domainservice.impl;

import cn.hutool.core.util.HashUtil;
import cool.xxd.infra.exceptions.BusinessException;
import cool.xxd.service.pay.domain.command.AddMerchantCommand;
import cool.xxd.service.pay.domain.domainservice.MerchantDomainService;
import cool.xxd.service.pay.domain.factory.MerchantFactory;
import cool.xxd.service.pay.domain.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantDomainServiceImpl implements MerchantDomainService {

    private final MerchantFactory merchantFactory;
    private final MerchantRepository merchantRepository;

    @Override
    public String addMerchant(AddMerchantCommand addMerchantCommand) {
        var merchantOptional = merchantRepository.findByAppIdAndOutMchid(addMerchantCommand.getAppid(), addMerchantCommand.getOutMchid());
        if (merchantOptional.isPresent()) {
            return merchantOptional.get().getMchid();
        }
        var mchid = generateMchid(addMerchantCommand.getOutMchid());
        merchantRepository.findByAppidAndMchid(addMerchantCommand.getAppid(), mchid).ifPresent(merchant -> {
            throw new BusinessException("商户号生成重复，请重新添加");
        });
        var merchant = merchantFactory.create(addMerchantCommand, mchid);
        merchantRepository.save(merchant);
        return merchant.getMchid();
    }

    private String generateMchid(String outMchid) {
        var timestamp = String.valueOf(System.currentTimeMillis());
        var uniqueStr = outMchid + timestamp;
        return String.valueOf(HashUtil.fnvHash(uniqueStr));
    }
}

