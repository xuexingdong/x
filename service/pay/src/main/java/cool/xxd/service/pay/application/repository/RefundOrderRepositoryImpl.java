package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.service.pay.application.converter.RefundOrderConverter;
import cool.xxd.service.pay.application.mapper.RefundOrderMapper;
import cool.xxd.service.pay.application.model.RefundOrderDO;
import cool.xxd.service.pay.domain.aggregate.RefundOrder;
import cool.xxd.service.pay.domain.enums.RefundStatusEnum;
import cool.xxd.service.pay.domain.query.RefundOrderQuery;
import cool.xxd.service.pay.domain.repository.RefundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefundOrderRepositoryImpl implements RefundOrderRepository {

    private final RefundOrderMapper refundOrderMapper;

    @Override
    public void save(RefundOrder refundOrder) {
        var refundOrderDO = RefundOrderConverter.INSTANCE.domain2do(refundOrder);
        refundOrderMapper.insert(refundOrderDO);
    }

    @Override
    public void deleteById(Long id) {
        var refundOrderDO = new RefundOrderDO();
        refundOrderDO.setId(id);
        refundOrderMapper.deleteById(refundOrderDO);
    }

    @Override
    public void update(RefundOrder refundOrder) {
        var refundOrderDO = RefundOrderConverter.INSTANCE.domain2do(refundOrder);
        refundOrderMapper.updateById(refundOrderDO);
    }

    @Override
    public boolean updateRefundResult(RefundOrder refundOrder, RefundStatusEnum fromRefundStatus) {
        var updateWrapper = Wrappers.lambdaUpdate(RefundOrderDO.class);
        updateWrapper.eq(RefundOrderDO::getId, refundOrder.getId())
                .eq(RefundOrderDO::getRefundStatus, fromRefundStatus.getCode())
                .set(refundOrder.getThirdTradeNo() != null, RefundOrderDO::getThirdTradeNo, refundOrder.getThirdTradeNo())
                .set(RefundOrderDO::getRefundStatus, refundOrder.getRefundStatus().getCode())
                .set(refundOrder.getPollingStartTime() != null, RefundOrderDO::getPollingStartTime, refundOrder.getPollingStartTime())
                .set(refundOrder.getRefundTime() != null, RefundOrderDO::getRefundTime, refundOrder.getRefundTime());
        return refundOrderMapper.update(updateWrapper) == 1;
    }

    @Override
    public Optional<RefundOrder> findByRefundOrderId(String mchid, Long refundOrderId) {
        var queryWrapper = Wrappers.lambdaQuery(RefundOrderDO.class);
        queryWrapper.eq(RefundOrderDO::getMchid, mchid);
        queryWrapper.eq(RefundOrderDO::getId, refundOrderId);
        var refundOrderDO = refundOrderMapper.selectOne(queryWrapper);
        var refundOrder = RefundOrderConverter.INSTANCE.do2domain(refundOrderDO);
        return Optional.ofNullable(refundOrder);
    }

    @Override
    public Optional<RefundOrder> findByRefundOrderNo(String refundOrderNo) {
        var queryWrapper = Wrappers.lambdaQuery(RefundOrderDO.class);
        queryWrapper.eq(RefundOrderDO::getRefundOrderNo, refundOrderNo);
        var refundOrderDO = refundOrderMapper.selectOne(queryWrapper);
        var refundOrder = RefundOrderConverter.INSTANCE.do2domain(refundOrderDO);
        return Optional.ofNullable(refundOrder);
    }

    @Override
    public Optional<RefundOrder> findById(Long id) {
        var refundOrderDO = refundOrderMapper.selectById(id);
        var refundOrder = RefundOrderConverter.INSTANCE.do2domain(refundOrderDO);
        return Optional.ofNullable(refundOrder);
    }

    @Override
    public List<RefundOrder> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var refundOrderDOList = refundOrderMapper.selectByIds(ids);
        return RefundOrderConverter.INSTANCE.do2domain(refundOrderDOList);
    }

    @Override
    public Optional<RefundOrder> findByMchidAndOutRefundNo(String mchid, String outRefundNo) {
        var queryWrapper = Wrappers.lambdaQuery(RefundOrderDO.class);
        queryWrapper.eq(RefundOrderDO::getAppid, mchid);
        queryWrapper.eq(RefundOrderDO::getOutRefundNo, outRefundNo);
        var refundOrderDO = refundOrderMapper.selectOne(queryWrapper);
        var refundOrder = RefundOrderConverter.INSTANCE.do2domain(refundOrderDO);
        return Optional.ofNullable(refundOrder);
    }

    @Override
    public List<RefundOrder> findByPayOrderNo(String payOrderNo) {
        var queryWrapper = Wrappers.lambdaQuery(RefundOrderDO.class);
        queryWrapper.eq(RefundOrderDO::getPayOrderNo, payOrderNo);
        var refundOrderDOList = refundOrderMapper.selectList(queryWrapper);
        return RefundOrderConverter.INSTANCE.do2domain(refundOrderDOList);
    }

    @Override
    public List<RefundOrder> query(RefundOrderQuery refundOrderQuery) {
        var queryWrapper = Wrappers.lambdaQuery(RefundOrderDO.class);
        if (refundOrderQuery.getAppid() != null) {
            queryWrapper.eq(RefundOrderDO::getAppid, refundOrderQuery.getAppid());
        }
        if (refundOrderQuery.getRefundStatus() != null) {
            queryWrapper.in(RefundOrderDO::getRefundStatus, refundOrderQuery.getRefundStatus().getCode());
        }
        var refundOrderDOList = refundOrderMapper.selectList(queryWrapper);
        return RefundOrderConverter.INSTANCE.do2domain(refundOrderDOList);
    }
}
