package cool.xxd.service.pay.application.converter;

import cool.xxd.service.pay.application.model.PayOrderDO;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.enums.TransModeEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface PayOrderConverter {
    PayOrderConverter INSTANCE = Mappers.getMapper(PayOrderConverter.class);

    PayOrder do2domain(PayOrderDO payOrderDO);

    List<PayOrder> do2domain(List<PayOrderDO> payOrderDOList);

    PayOrderDO domain2do(PayOrder payOrder);

    List<PayOrderDO> domain2do(List<PayOrder> payOrders);

    @AfterMapping
    default void afterMapping(@MappingTarget PayOrder payOrder, PayOrderDO payOrderDO) {
        if (payOrderDO.getRefundedAmount() == null) {
            payOrder.setRefundedAmount(BigDecimal.ZERO);
        }
        if (payOrderDO.getTransMode() == null) {
            payOrder.setTransMode(TransModeEnum.JSAPI);
        }
    }
}
