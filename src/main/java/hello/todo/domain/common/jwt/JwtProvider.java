package hello.todo.domain.common.jwt;

import hello.todo.domain.member.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProvider {

    private final Key secretKey;

    private Key getSecretKey() {
        return this.secretKey;
    }

    public String generateAccessToken(Long userId, Role role) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role",role.name())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDateAfter1hour())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDateAfter14Days())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date getExpirationDateAfter14Days(){
        return Date.from(Instant.now().plus(Duration.ofDays(14)));
    }

    private Date getExpirationDateAfter1hour(){
        return Date.from(Instant.now().plus(Duration.ofHours(1L)));
    }
}
