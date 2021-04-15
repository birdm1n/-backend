package com.daema.common.handler;

import com.daema.common.util.CommonUtil;
import com.daema.response.io.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        CommonResponse response = new CommonResponse(HttpStatus.UNAUTHORIZED.value(),"error","로그인이 되지 않은 사용자입니다.",null);

        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(CommonUtil.convertObjectToJson(response));
    }
}
