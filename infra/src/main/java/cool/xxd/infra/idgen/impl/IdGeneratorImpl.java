package cool.xxd.infra.idgen.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tencent.devops.leaf.service.SegmentService;
import cool.xxd.infra.idgen.IdGenerator;
import cool.xxd.infra.mybatis.XBaseDO;
import org.springframework.stereotype.Component;

@Component
public class IdGeneratorImpl implements IdGenerator {

    private final SegmentService segmentService;

    public IdGeneratorImpl(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @Override
    public <T extends XBaseDO> Long nextId(Class<T> clazz) {
        var tableName = clazz.getAnnotation(TableName.class);
        if (tableName != null && !tableName.value().isEmpty()) {
            return segmentService.getId(tableName.value()).getId();
        } else {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not have a TableName annotation");
        }
    }
}
