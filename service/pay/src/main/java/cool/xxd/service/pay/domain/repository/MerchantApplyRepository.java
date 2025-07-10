package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.MerchantApply;
import cool.xxd.service.pay.domain.enums.MerchantApplyStatusEnum;

import java.util.Optional;

public interface MerchantApplyRepository extends BaseRepository<MerchantApply, Long> {

    Optional<MerchantApply> findByMerchantApplyNo(String merchantApplyNo);

    boolean updateApplyStatus(MerchantApply merchantApply, MerchantApplyStatusEnum merchantApplyStatusEnum);
}
