package cool.xxd.mapstruct.idgen;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tencent.devops.leaf.service.SegmentService;
import org.springframework.stereotype.Component;

@Component
public class IdGeneratorImpl implements IdGenerator {

    private final SegmentService segmentService;

    public IdGeneratorImpl(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @Override
    public <T> long nextId(Class<T> clazz) {
        var tableName = clazz.getAnnotation(TableName.class);
        if (tableName != null && !tableName.value().isEmpty()) {
            return segmentService.getId(tableName.value()).getId();
        } else {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not have a TableName annotation");
        }
    }
}
