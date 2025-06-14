package hello.todo.domain.auth.application;

import hello.todo.domain.auth.domain.RefreshToken;
import hello.todo.domain.auth.domain.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void createRefreshToken(Long memberId, String token) {
        RefreshToken refreshToken = RefreshToken.of(memberId, token);
        refreshTokenRepository.save(refreshToken);
    }

}
