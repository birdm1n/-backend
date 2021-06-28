package com.daema.rest.common.util;

import com.daema.core.base.domain.Members;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 8;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24;

    final static public String AUTHORIZATION = "Authorization";
    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public Object getClaim(String token, String claimName, Class o){
        return extractAllClaims(token).get(claimName, o);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(Members member) {
        return doGenerateToken(member.getUsername(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Members member) {
        return doGenerateToken(member.getUsername(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(String username, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return buildJwt(claims, expireTime);
    }

    public String doGenerateTokenFromMap(HashMap<Object, Object> claimMap, long expireTime) {
        Claims claims = Jwts.claims();
        Iterator<Object> claimsList = claimMap.keySet().iterator();

        while (claimsList.hasNext()){
            String key = String.valueOf(claimsList.next());
            claims.put(key, claimMap.get(key));
        }

        return buildJwt(claims, expireTime);
    }

    private String buildJwt(Claims claims, long expireTime){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getAccessTokenFromHeader(HttpServletRequest request, String headerName){
        String authorization = request.getHeader(headerName);
        return authorization != null ? authorization.replace("Bearer ", "") : null;
    }

    public void setAccessTokenToHeader(HttpServletResponse response, String accessToken){
        response.addHeader("Authorization", "Bearer " + accessToken);
    }
}
