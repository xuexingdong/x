package cool.xxd.mapstruct.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
@RequiredArgsConstructor
public class RetrofitJackson {

    private final ObjectMapper objectMapper;

    @Bean
    public JacksonConverterFactory jacksonConverterFactory() {
        return JacksonConverterFactory.create(objectMapper);
    }
}
