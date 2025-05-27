package cool.xxd.infra.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignAlgorithm {
    MD5withRSA("MD5withRSA"),
    SHA256withRSA("SHA256withRSA")
    ;

    private final String value;

}
