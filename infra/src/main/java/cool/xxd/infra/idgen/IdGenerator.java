package cool.xxd.infra.idgen;

import cool.xxd.infra.mybatis.BaseDO;

public interface IdGenerator {

    <T extends BaseDO> Long nextId(Class<T> clazz);
}
