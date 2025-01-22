package cool.xxd.product.land.domain.aggregate;

import lombok.Data;

@Data
public class Material {
    private Long id;
    private String outMaterialId;
    private String content;
    private Long paperId;
}
