package cool.xxd.service.pay.application.converter;

import cool.xxd.service.pay.application.model.OrderLogDO;
import cool.xxd.service.pay.domain.aggregate.OrderLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface OrderLogConverter {
    OrderLogConverter INSTANCE = Mappers.getMapper(OrderLogConverter.class);

    OrderLog do2domain(OrderLogDO orderLogDO);

    List<OrderLog> do2domain(List<OrderLogDO> orderLogDOList);

    OrderLogDO domain2do(OrderLog orderLog);

    List<OrderLogDO> domain2do(List<OrderLog> orderLogs);
}
