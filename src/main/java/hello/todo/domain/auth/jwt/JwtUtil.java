package hello.todo.domain.auth.jwt;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


import java.security.Key;
import java.time.*;
import java.util.Date;


@RequiredArgsConstructor
public class JwtUtil {

    private final String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(Long userId) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDateAfter1hour())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDateAfter14Days())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public boolean validateTokenAndId(String token,Long userId) {
//        Long tokenUserId = getUserIdFromToken(token);
//        return (tokenUserId.equals(userId) && !isTokenExpired(token));
//    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getAllClaimsFromToken(token).getSubject());
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        } catch (JwtException e) {
            throw new CustomException(ErrorCode.JWT_INVALID);
        }
    }

    private Date getExpirationDateAfter14Days(){
        return Date.from(Instant.now().plus(Duration.ofDays(14)));
    }

    private Date getExpirationDateAfter1hour(){
        return Date.from(Instant.now().plus(Duration.ofHours(1L)));
    }


}
