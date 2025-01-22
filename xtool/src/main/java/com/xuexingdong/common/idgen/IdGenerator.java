package cool.xxd.mapstruct.idgen;

public interface IdGenerator {

    <T> long nextId(Class<T> clazz);
}
