package cool.xxd.service.pay.application.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.service.pay.application.model.OrderLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper extends XBaseMapper<OrderLogDO> {
}
