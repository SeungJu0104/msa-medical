package com.emr.slgi.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

public class JacksonTimeZoneConverter {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer timezoneCustomizer() { // KST를 Zone 데이터 가진 UTC로 변환(역직렬화)
        return builder -> builder.timeZone(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
