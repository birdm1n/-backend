package com.daema.web;

import com.daema.common.util.CookieUtil;
import com.daema.common.util.JwtUtil;
import com.daema.common.util.RedisUtil;
import com.daema.domain.Member;
import com.daema.domain.enums.UserRole;
import com.daema.dto.request.SocialDataRequest;
import com.daema.response.enums.ResponseCodeEnum;
import com.daema.response.io.CommonResponse;
import com.daema.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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
            final Member member = authService.loginSocialUser(socialData.getId(),socialData.getType());
            final String accessJwt = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);

            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

            /*
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, accessJwt);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            */

            cookieUtil.addHeaderCookie(res, JwtUtil.ACCESS_TOKEN_NAME, accessJwt);
            cookieUtil.addHeaderCookie(res, JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);

            HashMap<String, String> resMap = new HashMap<>();
            resMap.put("name", member.getName());

            //시스템 관리자인 경우에만 role key 생성
            if(UserRole.ROLE_ADMIN.equals(member.getRole())){
                resMap.put("role", "A");
            }

            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "로그인에 성공했습니다.", resMap);
        }catch(Exception e){
            response =  new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "로그인에 실패했습니다.", e.getMessage());
        }
        return response;
    }

}
