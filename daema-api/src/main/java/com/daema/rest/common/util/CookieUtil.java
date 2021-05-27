package com.daema.rest.common.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge((int)JwtUtil.TOKEN_VALIDATION_SECOND);
        token.setPath("/");

        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    //secure, sameSite 처리용으로 변경
    public void addHeaderCookie(HttpServletResponse res, String cookieName, String value){
        res.addHeader("Set-Cookie", makeResponseCookie(cookieName, value));
    }

    private String makeResponseCookie(String cookieName, String value){

        int maxAge = value == null ? 0 : (int)JwtUtil.TOKEN_VALIDATION_SECOND;

        // TODO 운영 도메인, 인증서 나오기 전까지 secure 적용 불가
        // 크롬 브라우저 옵션 변경으로 가능하나, 개별 사용자의 로컬 설정이 필요
        /*
        String profile = PropertiesValue.profilesActive;

        if(!"prod".equals(profile)) {
            return ResponseCookie.from(cookieName, value)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(maxAge)
                    .build().toString();
        }else{
            return ResponseCookie.from(cookieName, value)
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .maxAge(maxAge)
                    .path("/")
                    .build().toString();
        }
        */

        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .build().toString();
    }

}
