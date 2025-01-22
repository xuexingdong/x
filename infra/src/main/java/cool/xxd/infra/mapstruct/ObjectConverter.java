package cool.xxd.infra.mapstruct;

import java.util.List;

public interface ObjectConverter<S, T> {
    /**
     * 源对象转目标对象
     */
    T toTarget(S source);

    /**
     * 目标对象转源对象
     */
    S toSource(T target);

    /**
     * 源对象列表转目标对象列表
     */
    default List<T> toTargetList(List<S> sources) {
        if (sources == null) return List.of();
        return sources.stream()
                .map(this::toTarget)
                .toList();
    }

    /**
     * 目标对象列表转源对象列表
     */
    default List<S> toSourceList(List<T> targets) {
        if (targets == null) return List.of();
        return targets.stream()
                .map(this::toSource)
                .toList();
    }
}