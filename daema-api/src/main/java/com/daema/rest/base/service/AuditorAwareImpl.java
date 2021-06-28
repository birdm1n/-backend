package com.daema.rest.base.service;

import com.daema.core.base.domain.Members;
import com.daema.rest.base.dto.SecurityMember;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Members> {
    @Override
    public Optional<Members> getCurrentAuditor() {
				//Security에서 인증된 사용자 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication || !authentication.isAuthenticated()){
            return null;
        }

        return Optional.of(((SecurityMember)authentication.getPrincipal()).getMember());
    }
}