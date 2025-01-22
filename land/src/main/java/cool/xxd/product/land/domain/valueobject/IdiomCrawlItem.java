package cool.xxd.product.land.domain.valueobject;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IdiomCrawlItem implements Serializable {
    private String word;
    private List<Long> questionIds;
}
