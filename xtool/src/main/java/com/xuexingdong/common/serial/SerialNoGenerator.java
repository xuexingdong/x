package cool.xxd.mapstruct.serial;

public interface SerialNoGenerator {
    String generate(Integer digits);

    String generate(String key, Integer digits);
}
