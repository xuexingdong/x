package cool.xxd.product.land.infra.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.product.land.infra.model.PaperDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper extends XBaseMapper<PaperDO> {
}
