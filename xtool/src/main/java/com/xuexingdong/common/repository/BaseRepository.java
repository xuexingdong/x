package cool.xxd.mapstruct.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @param <T>        写成t，IDEA会自动填充变量小写名
 * @param <Identity> 写成Identity，IDEA会使用id作为参数，而不是aLong之类的奇怪命名
 */
public interface BaseRepository<T, Identity extends Serializable> {
    RuntimeException E = new UnsupportedOperationException("接口未实现");

    default void save(T t) {
        throw E;
    }

    /**
     * @param id 要删除的实体的ID
     */
    default void deleteById(Identity id) {
        throw E;
    }

    default void update(T t) {
        throw E;
    }

    Optional<T> findById(Identity id);

    default T getById(Identity id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %s not found!", id)));
    }

    List<T> findByIds(List<Identity> ids);
}
