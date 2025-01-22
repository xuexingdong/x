package cool.xxd.product.land.domain.valueobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdiomCrawlItem implements Serializable {
    private String word;
    private Long questionId;
}
