package cool.xxd.product.databox.domain.aggregate;

import lombok.Data;

@Data
public class CrawlTask {
    private Long id;
    private String name;
    private String spiderCode;
}
