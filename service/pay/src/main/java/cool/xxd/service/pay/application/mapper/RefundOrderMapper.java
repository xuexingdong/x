package cool.xxd.service.pay.application.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.service.pay.application.model.RefundOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundOrderMapper extends XBaseMapper<RefundOrderDO> {
}
