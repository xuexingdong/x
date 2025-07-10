package cool.xxd.infra.serial;

public interface SerialNoGenerator {
    String generate(String key);

    /**
     * @param key    流水号累计的key
     * @param digits 流水号位数，不足位数的会使用0补全
     * @return 流水号，返回比如000001类似的数字
     */
    String generate(String key, int digits);
}
