package com.daema.rest.config;

import com.daema.rest.common.Constants;
import com.daema.rest.common.handler.AccessFuncInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AccessFuncInterceptor accessFuncInterceptor;

    public WebConfig(AccessFuncInterceptor accessFuncInterceptor){
        this.accessFuncInterceptor = accessFuncInterceptor;
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessFuncInterceptor)
            .excludePathPatterns(Constants.SECURITY_PERMIT_ALL)
            .excludePathPatterns(Constants.SECURITY_EXCLUDE_URLS)
            .excludePathPatterns(Constants.SECURITY_WEB_IGNORE_URLS);
    }
}
