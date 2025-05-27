package cool.xxd.service.pay.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.hckj.pay.application.model.OrderLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLogDO> {
}
