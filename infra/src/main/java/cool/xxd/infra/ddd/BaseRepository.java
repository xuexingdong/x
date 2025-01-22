package cool.xxd.infra.ddd;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @param <T>        写成t，IDEA会自动填充变量小写名
 * @param <Identity> 写成Identity，IDEA会使用id作为参数，而不是aLong之类的奇怪命名
 */
public interface BaseRepository<T, Identity extends Serializable> {

    default void save(T t) {
        throw buildUnsupportedOperationException();
    }

    /**
     * @param id 要删除的实体的ID
     */
    default void deleteById(Identity id) {
        throw buildUnsupportedOperationException();
    }

    default void update(T t) {
        throw buildUnsupportedOperationException();
    }

    default Optional<T> findById(Identity id) {
        throw buildUnsupportedOperationException();
    }

    default T getById(Identity id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Entity with id %s not found!", id)));
    }

    default List<T> findByIds(Collection<Identity> ids) {
        throw buildUnsupportedOperationException();
    }

    private RuntimeException buildUnsupportedOperationException() {
        // 获取当前类的名称
        String className = getClass().getName();
        // 构建异常消息
        String message = String.format("Method not implemented in class '%s'", className);
        return new UnsupportedOperationException(message);
    }
}
