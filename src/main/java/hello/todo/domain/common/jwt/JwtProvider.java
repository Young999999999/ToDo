package hello.todo.domain.common.jwt;

import hello.todo.domain.common.jwt.dto.AuthToken;
import hello.todo.domain.member.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProvider {

    private final Key secretKey;

    private Key getSecretKey() {
        return this.secretKey;
    }

    private String generateAccessToken(Long memberId, Role role, Date issuedAt) {

        return Jwts.builder()
                .setSubject(memberId.toString())
                .claim("role", role.name())
                .setIssuedAt(issuedAt)
                .setExpiration(getExpirationDateAfter1hour(issuedAt))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Long memberId, Date issuedAt, Date accessTokenExpiration) {

        return Jwts.builder()
                .setSubject(memberId.toString())
                .claim("accessTokenExpiration", accessTokenExpiration)
                .setIssuedAt(issuedAt)
                .setExpiration(getExpirationDateAfter14Days(issuedAt))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public AuthToken generateAuthToken(Long memberId, Role role) {
        Date now = new Date();
        String accessToken = generateAccessToken(memberId, role, now);
        String refreshToken = generateRefreshToken(memberId, now, getExpirationDateAfter1hour(now));
        return AuthToken.of(accessToken, refreshToken);
    }

    private Date getExpirationDateAfter14Days(Date issuedAt) {
        return Date.from(issuedAt.toInstant().plus(Duration.ofDays(14)));
    }

    private Date getExpirationDateAfter1hour(Date issuedAt) {
        return Date.from(issuedAt.toInstant().plus(Duration.ofHours(1L)));
    }
}
