package com.daema.rest.common.filter;

import com.daema.base.domain.Members;
import com.daema.rest.base.dto.SecurityMember;
import com.daema.rest.base.service.MyUserDetailsService;
import com.daema.rest.commgmt.service.RoleFuncMgmtService;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.util.CookieUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.common.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;

    private final RoleFuncMgmtService roleFuncMgmtService;

    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    public JwtRequestFilter(MyUserDetailsService userDetailsService, RoleFuncMgmtService roleFuncMgmtService, JwtUtil jwtUtil, CookieUtil cookieUtil, RedisUtil redisUtil) {
        this.userDetailsService = userDetailsService;
        this.roleFuncMgmtService = roleFuncMgmtService;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.ACCESS_TOKEN_NAME);

        String username = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUname = null;

        try{
            if(jwtToken != null){
                jwt = jwtToken.getValue();
                username = jwtUtil.getUsername(jwt);
            }else{
                String profile = PropertiesValue.profilesActive;

                if(profile != null &&
                        (!"prod".equals(profile) &&
                                !"stag".equals(profile))) {

                    username = redisUtil.getData("localUserid");

                    //validation 통과용 강제 생성
                    jwt = jwtUtil.generateToken(Members.builder().username(username).build());
                }
            }
            if(username!=null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwt,userDetails)){

                    List<String> userFuncList = roleFuncMgmtService.getMemberEnableUrlPathList(((SecurityMember) userDetails).getMemberSeq(), ((SecurityMember) userDetails).getStoreId());
                    ((SecurityMember) userDetails).setMemberFuncList(userFuncList);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){
            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.REFRESH_TOKEN_NAME);
            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            if(refreshJwt != null){
                refreshUname = redisUtil.getData(refreshJwt);

                if(refreshUname == null){
                    redisUtil.deleteData(refreshJwt);
                    cookieUtil.addHeaderCookie(httpServletResponse, JwtUtil.REFRESH_TOKEN_NAME, null);
                }else {

                    if (refreshUname.equals(jwtUtil.getUsername(refreshJwt))) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUname);

                        List<String> userFuncList = roleFuncMgmtService.getMemberEnableUrlPathList(((SecurityMember) userDetails).getMemberSeq(), ((SecurityMember) userDetails).getStoreId());
                        ((SecurityMember) userDetails).setMemberFuncList(userFuncList);

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        Members member = new Members();
                        member.setUsername(refreshUname);
                        String newToken = jwtUtil.generateToken(member);

                        /*
                        Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME,newToken);
                        httpServletResponse.addCookie(newAccessToken);
                        */

                        cookieUtil.addHeaderCookie(httpServletResponse, JwtUtil.ACCESS_TOKEN_NAME, newToken);
                    }
                }
            }
        }catch(ExpiredJwtException e){
            e.printStackTrace();
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        AntPathMatcher pathMatcher = new AntPathMatcher();

        return Arrays.asList(Constants.SECURITY_EXCLUDE_URLS).stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }
}
