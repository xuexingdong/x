package cool.xxd.infra.crypto;

public class CryptoException extends RuntimeException {
    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(Throwable e) {
        super(e);
    }
}
