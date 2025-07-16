package com.emr.slgi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DateTimeConverterConfig implements WebMvcConfigurer { // PathVariable, 쿼리스트링 직렬화
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToKstLocalDateTimeConverter());
    }
    
    
}

