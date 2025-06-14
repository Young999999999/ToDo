package hello.todo.domain.auth.domain;

import hello.todo.domain.common.BaseTimeEntity;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    Long id;

    Long memberId;

    String token;

    private RefreshToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public static RefreshToken of(Long memberId, String token){
        return new RefreshToken(memberId,token);
    }

}
