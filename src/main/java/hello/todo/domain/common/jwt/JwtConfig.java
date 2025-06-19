package hello.todo.domain.common.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(Keys.hmacShaKeyFor(secretKey.getBytes()));
    }

    @Bean
    public JwtParser jwtParser() {
        return new JwtParser(Keys.hmacShaKeyFor(secretKey.getBytes()));
    }

}
