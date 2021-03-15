package com.daema.web;

import com.daema.common.util.CookieUtil;
import com.daema.common.util.JwtUtil;
import com.daema.common.util.RedisUtil;
import com.daema.domain.Member;
import com.daema.dto.request.SocialDataRequest;
import com.daema.service.AuthService;
import com.daema.response.io.CommonResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth")
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
            response = new CommonResponse("success","성공적으로 회원가입을 완료했습닌다.",null);
        }catch(Exception e){
            response = new CommonResponse("error","회원가입 실패",e.getMessage());
        }
        return response;
    }

    @GetMapping("/login/naver")
    public CommonResponse loginNaverUser(@RequestBody SocialDataRequest socialData, HttpServletRequest req, HttpServletResponse res){
        CommonResponse response;
        try{
            final Member member = authService.loginSocialUser(socialData.getId(),socialData.getType());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            response = new CommonResponse("success", "로그인에 성공했습니다.", token);
        }catch(Exception e){
            response =  new CommonResponse("error", "로그인에 실패했습니다.", e.getMessage());
        }
        return response;
    }

}
