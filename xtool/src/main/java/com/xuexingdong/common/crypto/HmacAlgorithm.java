package cool.xxd.mapstruct.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HmacAlgorithm {
    HmacSHA256("HmacSHA256"),
    ;

    private final String value;

}
