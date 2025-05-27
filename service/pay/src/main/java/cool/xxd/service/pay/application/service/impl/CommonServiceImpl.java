package cool.xxd.service.pay.application.service.impl;

import cool.xxd.infra.exceptions.BusinessException;
import group.hckj.pay.application.service.CommonService;
import cool.xxd.service.pay.domain.repository.AppRepository;
import cool.xxd.service.pay.domain.repository.MerchantRepository;
import group.hckj.pay.ui.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final AppRepository appRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public <T extends BaseRequest> void validateRequest(T request) {
        var app = appRepository.getByAppid(request.getAppid());
        if (app == null) {
            log.error("应用不存在，appid-{}", request.getAppid());
            throw new BusinessException("应用不存在");
        }
        if (request.getMchid() != null) {
            if (merchantRepository.findByAppidAndMchid(request.getAppid(), request.getMchid()).isEmpty()) {
                log.error("商户不存在，mchid-{}", request.getMchid());
                throw new BusinessException("商户不存在");
            }
        }
    }
}
