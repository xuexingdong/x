package cool.xxd.infra.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class LocalDateTimeJsonComponent {

    private static final String DF = "yyyy-MM-dd";
    private static final String TF = "HH:mm:ss";
    private static final String DTF = "yyyy-MM-dd HH:mm:ss";

    // LocalDate Serializer
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> implements ContextualSerializer {
        private final DateTimeFormatter formatter;

        public LocalDateSerializer() {
            this.formatter = DateTimeFormatter.ofPattern(DF);
        }

        public LocalDateSerializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(formatter));
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            return new LocalDateSerializer(resolveFormatter(property, DF));
        }
    }

    // LocalTime Serializer
    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> implements ContextualSerializer {
        private final DateTimeFormatter formatter;

        public LocalTimeSerializer() {
            this.formatter = DateTimeFormatter.ofPattern(TF);
        }

        public LocalTimeSerializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(formatter));
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            return new LocalTimeSerializer(resolveFormatter(property, TF));
        }
    }

    // LocalDateTime Serializer
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> implements ContextualSerializer {
        private final DateTimeFormatter formatter;

        public LocalDateTimeSerializer() {
            this.formatter = DateTimeFormatter.ofPattern(DTF);
        }

        public LocalDateTimeSerializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(formatter));
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            return new LocalDateTimeSerializer(resolveFormatter(property, DTF));
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> implements ContextualDeserializer {

        private final DateTimeFormatter formatter;

        public LocalDateDeserializer() {
            this.formatter = DateTimeFormatter.ofPattern(DF);
        }

        public LocalDateDeserializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), formatter);
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            return new LocalDateDeserializer(resolveFormatter(property, DF));
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> implements ContextualDeserializer {
        private final DateTimeFormatter formatter;

        public LocalTimeDeserializer() {
            this.formatter = DateTimeFormatter.ofPattern(TF);
        }

        public LocalTimeDeserializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalTime.parse(p.getValueAsString(), formatter);
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            return new LocalTimeDeserializer(resolveFormatter(property, TF));
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> implements ContextualDeserializer {
        private final DateTimeFormatter formatter;

        public LocalDateTimeDeserializer() {
            this.formatter = DateTimeFormatter.ofPattern(DTF);
        }

        public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), formatter);
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            return new LocalDateTimeDeserializer(resolveFormatter(property, DTF));
        }
    }

    // 抽取通用的逻辑到一个静态方法
    private static DateTimeFormatter resolveFormatter(BeanProperty property, String defaultPattern) {
        if (property != null) {
            var format = property.getAnnotation(JsonFormat.class);
            if (format != null && !format.pattern().isEmpty()) {
                return DateTimeFormatter.ofPattern(format.pattern());
            }
        }
        return DateTimeFormatter.ofPattern(defaultPattern); // 默认格式
    }
}