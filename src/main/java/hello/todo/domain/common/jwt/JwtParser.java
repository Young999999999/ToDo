package hello.todo.domain.common.jwt;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
public class JwtParser {

    private final Key secretKey;

    private Key getSecretKey() {
        return this.secretKey;
    }

    public Role getRoleFromToken(String token){
        Claims claims = getAllClaimsFromToken(token);
        String role = claims.get("role", String.class);
        return Role.valueOf(role);
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getAllClaimsFromToken(token).getSubject());
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        } catch (JwtException e) {
            throw new CustomException(ErrorCode.JWT_INVALID);
        }
    }

}
