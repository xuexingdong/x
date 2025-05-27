package cool.xxd.service.pay.domain.strategy.wechat.api.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public abstract class AbstractWechatNotifyRequest {
    private String id;
    private String createTime;
    private String eventType;
    private String resourceType;
    private Resource resource;
    private String summary;

    @JSONType(naming = PropertyNamingStrategy.SnakeCase)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Resource {
        private String algorithm;
        private String ciphertext;
        private String associatedData;
        private String originalType;
        private String nonce;
    }
}
