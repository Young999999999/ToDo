package hello.todo.domain.auth.application;

import hello.todo.domain.auth.domain.RefreshToken;
import hello.todo.domain.auth.domain.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void regist(Long memberId, String token) {
        RefreshToken refreshToken = RefreshToken.of(memberId, token);
        refreshTokenRepository.save(refreshToken);
    }

}
