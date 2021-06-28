package com.daema.rest.config;

import com.daema.core.base.domain.Members;
import com.daema.rest.base.service.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//spring security 환경에서만 생성자와 수정자 입력이 가능하다.
@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

    @Bean
    public AuditorAware<Members> auditorProvider(){
        return new AuditorAwareImpl();
    }


}