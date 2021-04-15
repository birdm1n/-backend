package com.daema.common.util;

import com.daema.domain.enums.UserRole;
import com.daema.dto.SecurityMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
     * 일반 사용자는 자신의 storeId 를 targetStoreId 로 사용
     * @param parameterStoreId
     * @return
     */
    public long getTargetStoreId(long parameterStoreId) {
        return isAdmin() ? parameterStoreId : getId("storeId");
    }

    private long getId(String type) {

        long returnId = 0;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityMember securityMember = (SecurityMember) authentication.getPrincipal();

            if("storeId".equals(type)){
                returnId = securityMember.getStoreId();
            }else if("memberSeq".equals(type)){
                returnId = securityMember.getMemberSeq();
            }
        }catch (Exception ignore){
            ignore.printStackTrace();
        }

        return returnId;
    }
}
