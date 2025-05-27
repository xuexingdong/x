package cool.xxd.service.pay.domain.repository;

import cool.xxd.infra.repository.BaseRepository;
import cool.xxd.service.pay.domain.aggregate.Merchant;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends BaseRepository<Merchant, Long> {

    Optional<Merchant> findByAppidAndMchid(String appid, String mchid);

    Optional<Merchant> findByAppIdAndOutMchid(String appid, String outMchid);

    List<Merchant> findByMchids(List<String> mchids);
}
