package cool.xxd.infra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public JsonJacksonCodec jsonJacksonCodec(ObjectMapper objectMapper) {
        return new JsonJacksonCodec(objectMapper);
    }
}