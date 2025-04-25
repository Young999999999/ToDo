package hello.todo.domain.auth.application;

import hello.todo.domain.auth.domain.RefreshToken;
import hello.todo.domain.auth.domain.RefreshTokenRepository;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.common.jwt.JwtParser;
import hello.todo.domain.common.jwt.JwtProvider;
import hello.todo.domain.common.jwt.dto.AuthToken;
import hello.todo.domain.member.application.MemberQueryService;
import hello.todo.domain.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReIssueRefershTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberQueryService memberQueryService;
    private final JwtParser jwtParser;
    private final JwtProvider jwtProvider;

    /**
     * DB의 저장된 리프레시 토큰을 바탕으로 인증 토큰을 재발급한다.
     * 이전에 같이 발급된 AcessToken이 만료되지 않았다면 RefreshToken의 부적절한 사용으로 인지하고 해당 토큰을 파기한다.
     */
    public AuthToken reIssueAuthToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        //에세스토큰 만료시간 검증
        jwtParser.validateRefreshTokenByAccessTokenExpiration(refreshToken.getToken());
        Long memberId = refreshToken.getMemberId();
        Role role = memberQueryService.findRoleByMemberId(memberId);
        return jwtProvider.generateAuthToken(memberId, role);
    }
}
