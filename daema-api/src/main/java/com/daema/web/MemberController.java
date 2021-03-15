package com.daema.web;

import com.daema.common.util.CookieUtil;
import com.daema.common.util.JwtUtil;
import com.daema.common.util.RedisUtil;
import com.daema.domain.Member;
import com.daema.dto.request.ChangePassword1Request;
import com.daema.dto.request.ChangePassword2Request;
import com.daema.dto.request.LoginUserRequest;
import com.daema.dto.request.VerifyEmailRequest;
import com.daema.service.AuthService;
import com.daema.response.io.CommonResponse;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
@RestController
@RequestMapping("/user")
public class MemberController {

    final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    public MemberController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService, CookieUtil cookieUtil, RedisUtil redisUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.cookieUtil = cookieUtil;
        this.redisUtil = redisUtil;
    }


    @PostMapping("/signup")
    public CommonResponse signUpUser(@RequestBody Member member) {
        try {
            authService.signUpUser(member);
            return new CommonResponse("success", "회원가입을 성공적으로 완료했습니다.", null);
        } catch (Exception e) {
            return new CommonResponse("error", "회원가입을 하는 도중 오류가 발생했습니다.", null);
        }
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginUserRequest user, HttpServletResponse res) {
        try {
            final Member member = authService.loginUser(user.getUsername(), user.getPassword());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            return new CommonResponse("success", "로그인에 성공했습니다.", token);
        } catch (Exception e) {
            return new CommonResponse("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }

    @PostMapping("/verify")
    public CommonResponse verify(@RequestBody VerifyEmailRequest requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {
        CommonResponse response;
        try {
            Member member = authService.findByUsername(requestVerifyEmail.getUsername());
            authService.sendVerificationMail(member);
            response = new CommonResponse("success", "성공적으로 인증메일을 보냈습니다.", null);
        } catch (Exception exception) {
            response = new CommonResponse("error", "인증메일을 보내는데 문제가 발생했습니다.", exception);
        }
        return response;
    }

    @GetMapping("/verify/{key}")
    public CommonResponse getVerify(@PathVariable String key) {
        CommonResponse response;
        try {
            authService.verifyEmail(key);
            response = new CommonResponse("success", "성공적으로 인증메일을 확인했습니다.", null);

        } catch (Exception e) {
            response = new CommonResponse("error", "인증메일을 확인하는데 실패했습니다.", null);
        }
        return response;
    }

    @GetMapping("/password/{key}")
    public CommonResponse isPasswordUUIdValidate(@PathVariable String key) {
        CommonResponse response;
        try {
            if (authService.isPasswordUuidValidate(key))
                response = new CommonResponse("success", "정상적인 접근입니다.", null);
            else
                response = new CommonResponse("error", "유효하지 않은 Key값입니다.", null);
        } catch (Exception e) {
            response = new CommonResponse("error", "유효하지 않은 key값입니다.", null);
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
            response = new CommonResponse("success", "성공적으로 사용자의 비밀번호 변경요청을 수행했습니다.", null);
        } catch (NoSuchFieldException e) {
            response = new CommonResponse("error", "사용자 정보를 조회할 수 없습니다.", null);
        } catch (Exception e) {
            response = new CommonResponse("error", "비밀번호 변경 요청을 할 수 없습니다.", null);
        }
        return response;
    }

    @PutMapping("/password")
    public CommonResponse changePassword(@RequestBody ChangePassword2Request requestChangePassword2) {
        CommonResponse response;
        try{
            Member member = authService.findByUsername(requestChangePassword2.getUsername());
            authService.changePassword(member,requestChangePassword2.getPassword());
            response = new CommonResponse("success","성공적으로 사용자의 비밀번호를 변경했습니다.",null);
        }catch(Exception e){
            response = new CommonResponse("error","사용자의 비밀번호를 변경할 수 없었습니다.",null);
        }
        return response;

    }

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
