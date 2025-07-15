package com.emr.slgi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AttachmentConfig implements WebMvcConfigurer { 
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/attachment/**")
            .allowedOrigins("http://localhost:5173") //배포시에 도메인 명시해주기 
            .allowedMethods("GET")
    		.allowedHeaders("*");
}
}