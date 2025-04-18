package hello.todo.domain.common.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil( @Value("${spring.jwt.secret-key}") String secret) {
        return new JwtUtil(secret);
    }
}
