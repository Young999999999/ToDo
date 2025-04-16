package hello.todo.domain.auth.jwt;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;


import java.security.Key;
import java.time.*;
import java.util.Date;
import java.util.Optional;


@RequiredArgsConstructor
public class JwtUtil {

    private final String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(Long userId, Role role) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role",role.name())
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

    public Role getRoleFromToken(String token){
        Claims claims = getAllClaimsFromToken(token);
        String role = claims.get("role", String.class);
        return Role.valueOf(role);

    }

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
