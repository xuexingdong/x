package cool.xxd.product.land.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.xxd.product.land.infra.model.PaperDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper extends BaseMapper<PaperDO> {
}
