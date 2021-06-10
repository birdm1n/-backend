package com.daema.rest.config;

import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.filter.HtmlCharacterEscapes;
import com.daema.rest.common.handler.AccessFuncInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AccessFuncInterceptor accessFuncInterceptor;
    private final ObjectMapper objectMapper;
    public WebConfig(AccessFuncInterceptor accessFuncInterceptor, ObjectMapper objectMapper){
        this.accessFuncInterceptor = accessFuncInterceptor;
        this.objectMapper = objectMapper;
    }

    /**
     * CORS 예외처리
     * prod 는 인증서버에서 처리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String profile = PropertiesValue.profilesActive;
        if(profile != null &&
                (!"prod".equals(profile) &&
                        !"stag".equals(profile))) {
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

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }

}
