package cool.xxd.service.pay.application.repository;

import group.hckj.pay.application.converter.OrderLogConverter;
import group.hckj.pay.application.mapper.OrderLogMapper;
import group.hckj.pay.application.model.OrderLogDO;
import cool.xxd.service.pay.domain.aggregate.OrderLog;
import cool.xxd.service.pay.domain.repository.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderLogRepositoryImpl implements OrderLogRepository {

    private final OrderLogMapper orderLogMapper;

    @Override
    public void save(OrderLog orderLog) {
        var orderLogDO = OrderLogConverter.INSTANCE.domain2do(orderLog);
        orderLogMapper.insert(orderLogDO);
    }

    @Override
    public void deleteById(Long id) {
        var orderLogDO = new OrderLogDO();
        orderLogDO.setId(id);
        orderLogMapper.deleteById(id);
    }

    @Override
    public void update(OrderLog orderLog) {
        var orderLogDO = OrderLogConverter.INSTANCE.domain2do(orderLog);
        orderLogMapper.updateById(orderLogDO);
    }

    @Override
    public Optional<OrderLog> findById(Long id) {
        var orderLogDO = orderLogMapper.selectById(id);
        var orderLog = OrderLogConverter.INSTANCE.do2domain(orderLogDO);
        return Optional.ofNullable(orderLog);
    }

    @Override
    public List<OrderLog> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var orderLogDOList = orderLogMapper.selectBatchIds(ids);
        return OrderLogConverter.INSTANCE.do2domain(orderLogDOList);
    }
}
