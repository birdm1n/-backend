package com.daema.rest.base.web;

import com.daema.base.domain.Member;
import com.daema.rest.base.dto.request.ChangePassword1Request;
import com.daema.rest.base.dto.request.ChangePassword2Request;
import com.daema.rest.base.dto.request.LoginUserRequest;
import com.daema.rest.base.dto.request.VerifyEmailRequest;
import com.daema.rest.base.service.AuthService;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.CookieUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.common.util.RedisUtil;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
@RestController
@RequestMapping(value = {"/user", "/api/user" })
public class MemberController {

    final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final AuthService authService;

    public MemberController(AuthenticationManager authenticationManager, AuthService authService, CookieUtil cookieUtil, RedisUtil redisUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.cookieUtil = cookieUtil;
        this.redisUtil = redisUtil;
    }


    @PostMapping("/signup")
    public CommonResponse signUpUser(@RequestBody Member member) {
        try {
            authService.signUpUser(member);
            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "회원가입을 성공적으로 완료했습니다.", null);
        } catch (Exception e) {
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "회원가입을 하는 도중 오류가 발생했습니다.", null);
        }
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginUserRequest user, HttpServletRequest req, HttpServletResponse res) {
        try {
            final Member member = authService.loginUser(user.getUsername(), user.getPassword());

            return authService.chkLoginMemberStatus(member, req, res);

        } catch (Exception e) {
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "로그인에 실패했습니다.", e.getMessage());
        }
    }

    @PostMapping("/verify")
    public CommonResponse verify(@RequestBody VerifyEmailRequest requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {
        CommonResponse response;
        try {
            Member member = authService.findByUsername(requestVerifyEmail.getUsername());
            authService.sendVerificationMail(member);
            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "성공적으로 인증메일을 보냈습니다.", null);
        } catch (Exception exception) {
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "인증메일을 보내는데 문제가 발생했습니다.", exception);
        }
        return response;
    }

    @GetMapping("/verify/{key}")
    public CommonResponse getVerify(@PathVariable String key) {
        CommonResponse response;
        try {
            authService.verifyEmail(key);
            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "성공적으로 인증메일을 확인했습니다.", null);

        } catch (Exception e) {
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "인증메일을 확인하는데 실패했습니다.", null);
        }
        return response;
    }

    @GetMapping("/password/{key}")
    public CommonResponse isPasswordUUIdValidate(@PathVariable String key) {
        CommonResponse response;
        try {
            if (authService.isPasswordUuidValidate(key))
                response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "정상적인 접근입니다.", null);
            else
                response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "유효하지 않은 Key값입니다.", null);
        } catch (Exception e) {
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "유효하지 않은 key값입니다.", null);
        }
        return response;
    }

    @PostMapping("/password")
    public CommonResponse requestChangePassword(@RequestBody ChangePassword1Request requestChangePassword1) {
        CommonResponse response;
        try {
            Member member = authService.findByUsername(requestChangePassword1.getUsername());
            if (!member.getEmail().equals(requestChangePassword1.getEmail())) throw new NoSuchFieldException("");
            authService.requestChangePassword(member);
            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "성공적으로 사용자의 비밀번호 변경요청을 수행했습니다.", null);
        } catch (NoSuchFieldException e) {
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "사용자 정보를 조회할 수 없습니다.", null);
        } catch (Exception e) {
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(), "비밀번호 변경 요청을 할 수 없습니다.", null);
        }
        return response;
    }

    @PutMapping("/password")
    public CommonResponse changePassword(@RequestBody ChangePassword2Request requestChangePassword2) {
        CommonResponse response;
        try{
            Member member = authService.findByUsername(requestChangePassword2.getUsername());
            authService.changePassword(member,requestChangePassword2.getPassword());
            response = new CommonResponse(ResponseCodeEnum.OK.getResultCode(),"성공적으로 사용자의 비밀번호를 변경했습니다.",null);
        }catch(Exception e){
            response = new CommonResponse(ResponseCodeEnum.FAIL.getResultCode(),"사용자의 비밀번호를 변경할 수 없었습니다.",null);
        }
        return response;

    }

    @PostMapping("/invalidate")
    public CommonResponse invalidate(HttpServletResponse res) {
        try {

            /*
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, null);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, null);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            */

            cookieUtil.addHeaderCookie(res, JwtUtil.ACCESS_TOKEN_NAME, null);
            cookieUtil.addHeaderCookie(res, JwtUtil.REFRESH_TOKEN_NAME, null);

            //로컬 ip 는 쿠키 문제로 redis 처리
            String profile = PropertiesValue.profilesActive;

            if(profile != null &&
                    !"prod".equals(profile)) {
                redisUtil.deleteData("localUserid");
            }

            return new CommonResponse(ResponseCodeEnum.OK.getResultCode());
        } catch (Exception e) {
            return new CommonResponse(ResponseCodeEnum.FAIL.getResultCode());
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
