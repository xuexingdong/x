package cool.xxd.product.msw.datafetcher.enums;

import cool.xxd.infra.enums.StringEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType implements StringEnum {
    SELL("sell", "出售"),
    BUY("buy", "购买"),
    ;

    private final String code;
    private final String name;
}

