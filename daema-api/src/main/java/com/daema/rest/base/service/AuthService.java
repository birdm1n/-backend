package com.daema.rest.base.service;

import com.daema.core.base.domain.Members;
import com.daema.core.base.domain.Salt;
import com.daema.core.base.domain.SocialData;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.UserRole;
import com.daema.core.base.repository.MemberRepository;
import com.daema.core.base.repository.SocialDataRepository;
import com.daema.rest.base.dto.request.SocialDataRequest;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.util.*;
import javassist.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    public void signUpUser(Members member) {
        String password = member.getPassword();
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }

    @Transactional
    public void signUpSocialUser(SocialDataRequest member){
        Members newMember = new Members();
        newMember.setUsername(member.getId());
        newMember.setPassword("");
        newMember.setEmail(member.getEmail());
        newMember.setName(member.getName());
        newMember.setAddress("");
        newMember.setSocial(new SocialData(member.getId(),member.getEmail(),member.getType()));
        memberRepository.save(newMember);
    }

    public Members loginSocialUser(String id, String type) throws NotFoundException{
        SocialData socialData = socialDataRepository.findBySocialDataIdAndSocialType(id,type);
        if(socialData == null) throw new NotFoundException("????????? ???????????? ??????");
        return socialData.getMember();
    }

    public Members loginUser(String id, String password) throws Exception{
        Members member = memberRepository.findByUsername(id);
        if(member == null) throw new Exception ("????????? ???????????? ??????");
        String salt = member.getSalt().getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getPassword().equals(password)) throw new Exception ("??????????????? ????????????.");
        if(member.getSocial() != null) throw new Exception ("?????? ???????????? ????????? ????????????.");
        return member;
    }

    public Members findByUsername(String username) throws NotFoundException {
        Members member = memberRepository.findByUsername(username);
        if(member == null) throw new NotFoundException("????????? ???????????? ??????");
        return member;
    }

    public void verifyEmail(String key) throws NotFoundException {
        String memberId = redisUtil.getData(key);
        Members member = memberRepository.findByUsername(memberId);
        if(member == null) throw new NotFoundException("????????? ??????????????????");
        modifyUserRole(member, UserRole.ROLE_USER);
        redisUtil.deleteData(key);
    }

    public void sendVerificationMail(Members member) throws NotFoundException {
        String VERIFICATION_LINK = "http://localhost:8080/user/verify/";
        if(member == null) throw new NotFoundException("????????? ???????????? ??????");
        UUID uuid = UUID.randomUUID();
        redisUtil.setDataExpire(uuid.toString(),member.getUsername(), 60 * 30L);
        emailUtil.sendMail(member.getEmail(),"???????????? ?????????????????????.",VERIFICATION_LINK + uuid.toString());
    }

    public void modifyUserRole(Members member, UserRole userRole){
        member.setRole(userRole);
        memberRepository.save(member);
    }

    public boolean isPasswordUuidValidate(String key){
        String memberId = redisUtil.getData(key);
        return !memberId.equals("");
    }

    public void changePassword(Members member, String password) throws NotFoundException{
        if(member == null) throw new NotFoundException("changePassword(),????????? ???????????? ??????");
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }


    public void requestChangePassword(Members member) throws NotFoundException{
        String CHANGE_PASSWORD_LINK = "http://localhost:8080/user/password/";
        if(member == null) throw new NotFoundException("????????? ???????????? ??????.");
        String key = REDIS_CHANGE_PASSWORD_PREFIX + UUID.randomUUID();
        redisUtil.setDataExpire(key,member.getUsername(),60 * 30L);
        //TODO ?????? ?????? ??????
        //emailUtil.sendMail(member.getEmail(),"????????? ???????????? ?????? ??????",CHANGE_PASSWORD_LINK + key);
    }

    public CommonResponse chkLoginMemberStatus(Members member, HttpServletRequest req, HttpServletResponse res) throws Exception {

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

            //????????? ???????????? ???????????? role key ??????
            if(UserRole.ROLE_ADMIN.equals(member.getRole())){
                resMap.put("role", "A");
            }

            //?????? ip ??? ?????? ????????? redis ??????
            String profile = PropertiesValue.profilesActive;

            if(profile != null &&
                    (!"prod".equals(profile) &&
                            !"stag".equals(profile))) {
                redisUtil.setData("localUserid", member.getUsername());
            }

            return new CommonResponse(ResponseCodeEnum.OK.getResultCode(), "???????????? ??????????????????.", resMap);

        }else if(String.valueOf(StatusEnum.USER_REG.getStatusCode()).equals(member.getUserStatus())){

            return new CommonResponse(ResponseCodeEnum.NOT_APPROVAL_USER.getResultCode(), ResponseCodeEnum.NOT_APPROVAL_USER.getResultMsg(), null);

        }else{
            throw new Exception("");
        }
    }

}