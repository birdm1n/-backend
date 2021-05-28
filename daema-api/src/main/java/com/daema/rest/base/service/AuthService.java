package com.daema.rest.base.service;

import com.daema.base.domain.Member;
import com.daema.base.domain.Salt;
import com.daema.base.domain.SocialData;
import com.daema.base.enums.StatusEnum;
import com.daema.base.enums.UserRole;
import com.daema.base.repository.MemberRepository;
import com.daema.base.repository.SocialDataRepository;
import com.daema.rest.base.dto.request.SocialDataRequest;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.*;
import javassist.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

@Log
@Service
public class AuthService {
    final String REDIS_CHANGE_PASSWORD_PREFIX="CPW";

    private final MemberRepository memberRepository;

    private final EmailUtil emailUtil;

    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

    private final SaltUtil saltUtil;

    private final RedisUtil redisUtil;

    private final SocialDataRepository socialDataRepository;

    public AuthService(MemberRepository memberRepository, EmailUtil emailUtil, JwtUtil jwtUtil, CookieUtil cookieUtil, SaltUtil saltUtil, RedisUtil redisUtil, SocialDataRepository socialDataRepository) {
        this.memberRepository = memberRepository;
        this.emailUtil = emailUtil;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.saltUtil = saltUtil;
        this.redisUtil = redisUtil;
        this.socialDataRepository = socialDataRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void signUpUser(Member member) {
        String password = member.getPassword();
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }

    @Transactional
    public void signUpSocialUser(SocialDataRequest member){
        Member newMember = new Member();
        newMember.setUsername(member.getId());
        newMember.setPassword("");
        newMember.setEmail(member.getEmail());
        newMember.setName(member.getName());
        newMember.setAddress("");
        newMember.setSocial(new SocialData(member.getId(),member.getEmail(),member.getType()));
        memberRepository.save(newMember);
    }

    public Member loginSocialUser(String id, String type) throws NotFoundException{
        SocialData socialData = socialDataRepository.findBySocialDataIdAndSocialType(id,type);
        if(socialData == null) throw new NotFoundException("멤버가 조회되지 않음");
        return socialData.getMember();
    }

    public Member loginUser(String id, String password) throws Exception{
        Member member = memberRepository.findByUsername(id);
        if(member == null) throw new Exception ("멤버가 조회되지 않음");
        String salt = member.getSalt().getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getPassword().equals(password)) throw new Exception ("비밀번호가 틀립니다.");
        if(member.getSocial() != null) throw new Exception ("소셜 계정으로 로그인 해주세요.");
        return member;
    }

    public Member findByUsername(String username) throws NotFoundException {
        Member member = memberRepository.findByUsername(username);
        if(member == null) throw new NotFoundException("멤버가 조회되지 않음");
        return member;
    }

    public void verifyEmail(String key) throws NotFoundException {
        String memberId = redisUtil.getData(key);
        Member member = memberRepository.findByUsername(memberId);
        if(member == null) throw new NotFoundException("멤버가 조회되지않음");
        modifyUserRole(member, UserRole.ROLE_USER);
        redisUtil.deleteData(key);
    }

    public void sendVerificationMail(Member member) throws NotFoundException {
        String VERIFICATION_LINK = "http://localhost:8080/user/verify/";
        if(member == null) throw new NotFoundException("멤버가 조회되지 않음");
        UUID uuid = UUID.randomUUID();
        redisUtil.setDataExpire(uuid.toString(),member.getUsername(), 60 * 30L);
        emailUtil.sendMail(member.getEmail(),"회원가입 인증메일입니다.",VERIFICATION_LINK + uuid.toString());
    }

    public void modifyUserRole(Member member, UserRole userRole){
        member.setRole(userRole);
        memberRepository.save(member);
    }

    public boolean isPasswordUuidValidate(String key){
        String memberId = redisUtil.getData(key);
        return !memberId.equals("");
    }

    public void changePassword(Member member,String password) throws NotFoundException{
        if(member == null) throw new NotFoundException("changePassword(),멤버가 조회되지 않음");
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }


    public void requestChangePassword(Member member) throws NotFoundException{
        String CHANGE_PASSWORD_LINK = "http://localhost:8080/user/password/";
        if(member == null) throw new NotFoundException("멤버가 조회되지 않음.");
        String key = REDIS_CHANGE_PASSWORD_PREFIX + UUID.randomUUID();
        redisUtil.setDataExpire(key,member.getUsername(),60 * 30L);
        //TODO 메일 발송 확인
        //emailUtil.sendMail(member.getEmail(),"사용자 비밀번호 안내 메일",CHANGE_PASSWORD_LINK + key);
    }

    public CommonResponse chkLoginMemberStatus(Member member, HttpServletResponse res) throws Exception {

        if(String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode()).equals(member.getUserStatus())
                && (UserRole.ROLE_USER == member.getRole()
                || UserRole.ROLE_MANAGER == member.getRole()
                || UserRole.ROLE_ADMIN == member.getRole())){

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

            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "로그인에 성공했습니다.", resMap);

        }else if(String.valueOf(StatusEnum.USER_REG.getStatusCode()).equals(member.getUserStatus())){

            return new CommonResponse(ResponseCodeEnum.NOT_APPROVAL_USER.getResultCode(), ResponseCodeEnum.NOT_APPROVAL_USER.getResultMsg(), null);

        }else{
            throw new Exception("");
        }
    }

}