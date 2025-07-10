package cool.xxd.service.pay.application.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.xxd.infra.page.PageRequest;
import cool.xxd.infra.page.PageResult;
import cool.xxd.service.pay.application.converter.PayOrderConverter;
import cool.xxd.service.pay.application.mapper.PayOrderMapper;
import cool.xxd.service.pay.application.model.PayOrderDO;
import cool.xxd.service.pay.domain.aggregate.PayOrder;
import cool.xxd.service.pay.domain.enums.PayStatusEnum;
import cool.xxd.service.pay.domain.query.PayOrderQuery;
import cool.xxd.service.pay.domain.repository.PayOrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PayOrderRepositoryImpl implements PayOrderRepository {

    private final PayOrderMapper payOrderMapper;

    @Override
    public void save(PayOrder payOrder) {
        var payOrderDO = PayOrderConverter.INSTANCE.domain2do(payOrder);
        payOrderMapper.insert(payOrderDO);
    }

    @Override
    public void deleteById(Long id) {
        var payOrderDO = new PayOrderDO();
        payOrderDO.setId(id);
        payOrderMapper.deleteById(payOrderDO);
    }

    @Override
    public void update(PayOrder payOrder) {
        var payOrderDO = PayOrderConverter.INSTANCE.domain2do(payOrder);
        payOrderMapper.updateById(payOrderDO);
    }

    @Override
    public void updateRefundedAmount(PayOrder payOrder) {
        var updateWrapper = Wrappers.lambdaUpdate(PayOrderDO.class);
        updateWrapper.eq(PayOrderDO::getMchid, payOrder.getMchid())
                .eq(PayOrderDO::getId, payOrder.getId())
                .set(PayOrderDO::getRefundedAmount, payOrder.getRefundedAmount());
        payOrderMapper.update(updateWrapper);
    }

    @Override
    public boolean updatePayResult(PayOrder payOrder, PayStatusEnum fromPayStatus) {
        var updateWrapper = Wrappers.lambdaUpdate(PayOrderDO.class);
        updateWrapper.eq(PayOrderDO::getMchid, payOrder.getMchid())
                .eq(PayOrderDO::getId, payOrder.getId())
                .eq(PayOrderDO::getPayStatus, fromPayStatus.getCode())
                .set(payOrder.getThirdTradeNo() != null, PayOrderDO::getThirdTradeNo, payOrder.getThirdTradeNo())
                .set(payOrder.getQrCode() != null, PayOrderDO::getQrCode, payOrder.getQrCode())
                .set(payOrder.getClientPayInvokeParams() != null, PayOrderDO::getClientPayInvokeParams, payOrder.getClientPayInvokeParams())
                .set(PayOrderDO::getPayStatus, payOrder.getPayStatus().getCode())
                .set(payOrder.getPollingStartTime() != null, PayOrderDO::getPollingStartTime, payOrder.getPollingStartTime())
                .set(payOrder.getPayTime() != null, PayOrderDO::getPayTime, payOrder.getPayTime());
        return payOrderMapper.update(updateWrapper) == 1;
    }

    @Override
    public Optional<PayOrder> findById(String mchid, Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<PayOrder> findByPayOrderNo(String mchid, String payOrderNo) {
        return Optional.empty();
    }

    @Override
    public Optional<PayOrder> findById(Long id) {
        var payOrderDO = payOrderMapper.selectById(id);
        var payOrder = PayOrderConverter.INSTANCE.do2domain(payOrderDO);
        return Optional.ofNullable(payOrder);
    }

    @Override
    public List<PayOrder> findByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var payOrderDOList = payOrderMapper.selectByIds(ids);
        return PayOrderConverter.INSTANCE.do2domain(payOrderDOList);
    }

    @Override
    public Optional<PayOrder> findByPayOrderNo(String payOrderNo) {
        var queryWrapper = Wrappers.lambdaQuery(PayOrderDO.class);
        queryWrapper.eq(PayOrderDO::getPayOrderNo, payOrderNo);
        var payOrderDO = payOrderMapper.selectOne(queryWrapper);
        var payOrder = PayOrderConverter.INSTANCE.do2domain(payOrderDO);
        return Optional.ofNullable(payOrder);
    }

    @Override
    public Optional<PayOrder> findByOutTradeNo(String mchid, String outTradeNo) {
        var queryWrapper = Wrappers.lambdaQuery(PayOrderDO.class);
        queryWrapper.eq(PayOrderDO::getMchid, mchid);
        queryWrapper.eq(PayOrderDO::getOutTradeNo, outTradeNo);
        var payOrderDO = payOrderMapper.selectOne(queryWrapper);
        var payOrder = PayOrderConverter.INSTANCE.do2domain(payOrderDO);
        return Optional.ofNullable(payOrder);
    }

    @Override
    public Optional<PayOrder> findByPayOrderNoAndOutTradeNo(String mchid, String payOrderNo, String outTradeNo) {
        if (StringUtils.isNotEmpty(payOrderNo)) {
            return findByPayOrderNo(payOrderNo);
        }
        if (StringUtils.isNotEmpty(outTradeNo)) {
            return findByOutTradeNo(mchid, outTradeNo);
        }
        return Optional.empty();
    }

    @Override
    public List<PayOrder> query(PayOrderQuery payOrderQuery) {
        var queryWrapper = buildQuery(payOrderQuery);
        var payOrderDOList = payOrderMapper.selectList(queryWrapper);
        return PayOrderConverter.INSTANCE.do2domain(payOrderDOList);
    }

    @Override
    public PageResult<PayOrder> query(PayOrderQuery payOrderQuery, PageRequest pageRequest) {
        var queryWrapper = buildQuery(payOrderQuery);
        var payOrderDOIPage = payOrderMapper.selectPage(pageRequest.toIPage(), queryWrapper);
        var payOrders = PayOrderConverter.INSTANCE.do2domain(payOrderDOIPage.getRecords());
        return PageResult.of(payOrders, payOrderDOIPage.getTotal());
    }

    private static LambdaQueryWrapper<PayOrderDO> buildQuery(PayOrderQuery payOrderQuery) {
        var queryWrapper = Wrappers.lambdaQuery(PayOrderDO.class);
        queryWrapper.eq(PayOrderDO::getMchid, payOrderQuery.getMchid());
        if (StringUtils.isNotBlank(payOrderQuery.getAppid())) {
            queryWrapper.eq(PayOrderDO::getAppid, payOrderQuery.getAppid());
        }
        if (payOrderQuery.getPayStatusList() != null && !payOrderQuery.getPayStatusList().isEmpty()) {
            queryWrapper.in(PayOrderDO::getPayStatus, payOrderQuery.getPayStatusList().stream()
                    .map(PayStatusEnum::getCode).toList());
        }
        if (payOrderQuery.getFromTimeExpire() != null) {
            queryWrapper.ge(PayOrderDO::getTimeExpire, payOrderQuery.getFromTimeExpire());
        }
        if (payOrderQuery.getToTimeExpire() != null) {
            queryWrapper.le(PayOrderDO::getTimeExpire, payOrderQuery.getToTimeExpire());
        }
        if (payOrderQuery.getFromPollingStartTime() != null) {
            queryWrapper.ge(PayOrderDO::getPollingStartTime, payOrderQuery.getFromPollingStartTime());
        }
        if (payOrderQuery.getToPollingStartTime() != null) {
            queryWrapper.le(PayOrderDO::getPollingStartTime, payOrderQuery.getToPollingStartTime());
        }
        return queryWrapper;
    }
}
