package cool.xxd.service.pay.application.converter;

import group.hckj.pay.application.model.MerchantDO;
import cool.xxd.service.pay.domain.aggregate.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MerchantConverter {
    MerchantConverter INSTANCE = Mappers.getMapper(MerchantConverter.class);

    Merchant do2domain(MerchantDO merchantDO);

    List<Merchant> do2domain(List<MerchantDO> merchantDOList);

    MerchantDO domain2do(Merchant merchant);

    List<MerchantDO> domain2do(List<Merchant> merchants);
}
