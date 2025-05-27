package cool.xxd.service.pay.application.converter;

import group.hckj.pay.application.model.RefundOrderDO;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface RefundOrderConverter {
    RefundOrderConverter INSTANCE = Mappers.getMapper(RefundOrderConverter.class);

    RefundOrder do2domain(RefundOrderDO refundOrderDO);

    List<RefundOrder> do2domain(List<RefundOrderDO> refundOrderDOList);

    RefundOrderDO domain2do(RefundOrder refundOrder);

    List<RefundOrderDO> domain2do(List<RefundOrder> refundOrders);

}
