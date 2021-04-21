package com.daema.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 예외처리
     * prod 는 인증서버에서 처리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String profile = System.getProperty("spring.profiles.active");
        if(profile != null &&
                !"prod".equals(profile)) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*");
        }
    }
}
