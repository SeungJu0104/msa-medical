package com.emr.slgi.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class JacksonDateTimeConfig {
    // Body의 JSON 데이터 내 시간을 직렬화 역직렬화
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer kstDeserializer() { // UTC의 Zone 데이터를 가지고 KST로 변환(직렬화)
        return builder -> builder.deserializers(new StdDeserializer<LocalDateTime>(LocalDateTime.class) {
            @Override
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                OffsetDateTime odt = OffsetDateTime.parse(p.getText());
                return odt.atZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
            }
        });
    }

}

