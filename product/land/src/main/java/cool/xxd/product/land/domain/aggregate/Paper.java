package cool.xxd.product.land.domain.aggregate;

import lombok.Data;

@Data
public class Paper {
    private Long id;
    private String outPaperId;
    private String name;
    private Integer questionCount;
}
