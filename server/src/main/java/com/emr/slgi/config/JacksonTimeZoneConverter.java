package com.emr.slgi.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

public class JacksonTimeZoneConverter {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer timezoneCustomizer() {
        return builder -> builder.timeZone(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
