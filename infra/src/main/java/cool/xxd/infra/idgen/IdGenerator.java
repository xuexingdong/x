package cool.xxd.infra.idgen;

import cool.xxd.infra.mybatis.XBaseDO;

public interface IdGenerator {

    <T extends XBaseDO> Long nextId(Class<T> clazz);
}
