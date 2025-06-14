package hello.todo.domain.common.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtProvider jwtProvider(@Value("${spring.jwt.secret-key}") String secretKey) {
        return new JwtProvider(Keys.hmacShaKeyFor(secretKey.getBytes()));
    }

    @Bean
    public JwtParser jwtParser(@Value("${spring.jwt.secret-key}") String secretKey) {
        return new JwtParser(Keys.hmacShaKeyFor(secretKey.getBytes()));
    }

}
