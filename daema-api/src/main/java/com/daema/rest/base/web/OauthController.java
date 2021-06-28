package com.daema.rest.base.web;

import com.daema.core.base.domain.Members;
import com.daema.rest.base.dto.request.SocialDataRequest;
import com.daema.rest.base.service.AuthService;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.CookieUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.common.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/oauth", "/api/oauth" })
public class OauthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    public OauthController(AuthService authService, JwtUtil jwtUtil, CookieUtil cookieUtil, RedisUtil redisUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.redisUtil = redisUtil;
    }

    @PostMapping("/signup/naver")
    public CommonResponse signUpNaverUser(@RequestBody SocialDataRequest socialData){
        CommonResponse response;
        try{
            authService.signUpSocialUser(socialData);
            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(),"성공적으로 회원가입을 완료했습닌다.",null);
        }catch(Exception e){
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(),"회원가입 실패",e.getMessage());
        }
        return response;
    }

    @GetMapping("/login/naver")
    public CommonResponse loginNaverUser(@RequestBody SocialDataRequest socialData, HttpServletRequest req, HttpServletResponse res){
        CommonResponse response;
        try{
            final Members member = authService.loginSocialUser(socialData.getId(),socialData.getType());

            return authService.chkLoginMemberStatus(member, req, res);

        }catch(Exception e){
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "로그인에 실패했습니다.", e.getMessage());
        }
    }

}
