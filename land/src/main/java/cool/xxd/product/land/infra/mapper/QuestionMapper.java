package cool.xxd.product.land.infra.mapper;

import cool.xxd.infra.mybatis.XBaseMapper;
import cool.xxd.product.land.infra.model.QuestionDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends XBaseMapper<QuestionDO> {
}
