package com.daema.rest.common.util;

import com.daema.base.enums.UserRole;
import com.daema.rest.base.dto.SecurityMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationUtil {

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
    }

    public boolean hasRole(String userRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(userRole));
    }

    public long getMemberSeq() {
        return getId("memberSeq");
    }

    public long getStoreId() {
        return getId("storeId");
    }

    /**
     * 시스템관리자는 request 받은 parameterStoreId(관리점) 를 사용
     * anonymous 는 request 받은 parameterStoreId(신규 관리점) 을 사용
     * 일반 사용자는 자신의 storeId 를 targetStoreId 로 사용
     * @param parameterStoreId
     * @return
     */
    public long getTargetStoreId(long parameterStoreId) {
        return isAdmin() || hasRole(UserRole.ROLE_ANONYMOUS.name()) ? parameterStoreId : getId("storeId");
    }

    private long getId(String type) {

        long returnId = 0;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!(authentication.getPrincipal() instanceof String)) {
                SecurityMember securityMember = (SecurityMember) authentication.getPrincipal();

                if ("storeId".equals(type)) {
                    returnId = securityMember.getStoreId();
                } else if ("memberSeq".equals(type)) {
                    returnId = securityMember.getMemberSeq();
                }
            }
        }catch (Exception ignore){
            ignore.printStackTrace();
        }

        return returnId;
    }

    public List<String> getEnableUrlPath(){

        List<String> urlPath = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication.getPrincipal() instanceof String)) {
            SecurityMember securityMember = (SecurityMember) authentication.getPrincipal();
            urlPath = securityMember.getMemberFuncList();
        }

        return urlPath;
    }
}
