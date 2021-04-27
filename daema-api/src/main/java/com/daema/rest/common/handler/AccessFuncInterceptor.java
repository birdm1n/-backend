package com.daema.rest.common.handler;

import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.RedisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessFuncInterceptor implements HandlerInterceptor {

    private final AuthenticationUtil authenticationUtil;
    private final RedisUtil redisUtil;

    public AccessFuncInterceptor(AuthenticationUtil authenticationUtil, RedisUtil redisUtil) {
        this.authenticationUtil = authenticationUtil;
        this.redisUtil = redisUtil;
    }

    /**
     * 기능 접근 권한 체크
     * DataHandleService.extractApiFunc 에서 관리대상 URL을 RequestMappingHandlerMapping 을 통해 redis 에 저장함
     * 1. 관리자
     * 2. 관리대상 url 이 아님
     * 3. 관리대상 url 이며, 사용자 접근 권한이 있다
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        if(authenticationUtil.isAdmin()
                || !StringUtils.hasText(redisUtil.getData(uri))
                || (StringUtils.hasText(redisUtil.getData(uri))
                        && authenticationUtil.getEnableUrlPath().contains(uri))){
            return true;
        }else{
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(CommonUtil.convertObjectToJson(new CommonResponse(HttpStatus.UNAUTHORIZED.value(), ResponseCodeEnum.UNAUTHORIZED_FUNC.getResultCode(), ResponseCodeEnum.UNAUTHORIZED_FUNC.getResultMsg(),null)));
            return false;
        }
    }
}
