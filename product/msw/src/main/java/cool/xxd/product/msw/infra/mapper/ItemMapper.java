package cool.xxd.product.msw.infra.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.product.msw.infra.model.ItemDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper extends XBaseMapper<ItemDO> {
}
