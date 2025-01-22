package cool.xxd.infra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public TypedJsonJacksonCodec typedJsonJacksonCodec(ObjectMapper objectMapper) {
        return new TypedJsonJacksonCodec(Object.class, objectMapper);
    }
}