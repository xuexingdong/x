package cool.xxd.service.pay.application.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.service.pay.application.model.MerchantDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MerchantMapper extends XBaseMapper<MerchantDO> {
}
