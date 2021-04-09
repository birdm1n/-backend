package com.daema.common.util;

import com.daema.domain.User2;
import com.daema.domain.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
//TODO 테이블 변경 등 기능 재정리 필요
public class AuthenticationUtil {

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
    }

    public boolean hasRole(String userRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(userRole));
    }

    public long getId(String type) {

        long returnId = 0;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User2 user = (User2) authentication.getPrincipal();

            if("storeId".equals(type)){
                returnId = user.getStoreId();
            }else if("memberSeq".equals(type)){
                returnId = user.getUserId();
            }
        }catch (Exception ignore){
            ignore.printStackTrace();
        }

        return returnId;
    }
}
