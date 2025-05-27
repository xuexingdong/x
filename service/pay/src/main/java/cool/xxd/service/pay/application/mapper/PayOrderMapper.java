package cool.xxd.service.pay.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.hckj.pay.application.model.PayOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrderDO> {
}
