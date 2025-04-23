package hello.todo.domain.auth.application;


import hello.todo.domain.auth.presentation.dto.response.SignUpResponse;
import hello.todo.domain.common.jwt.JwtProvider;
import hello.todo.domain.common.jwt.dto.AuthToken;
import hello.todo.domain.member.application.CreateMemberService;
import hello.todo.domain.member.application.MemberQueryService;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoogleOAuthService {

    private final OAuthClient oAuthClient;
    private final CreateMemberService createMemberService;
    private final MemberQueryService memberQueryService;
    private final JwtProvider jwtProvider;
    private final CreateRefreshTokenService createRefreshTokenService;

    public SignUpResponse signUp(String code) {
        MultiValueMap<String, String> map = buildTokenRequestMap(code);
        String sub = oAuthClient.exchangeCodeToSub(map);

        Long memberId = memberQueryService.findBySub(sub)
                .map(Member::getId)
                .orElseGet(() -> {
                    log.info("신규 멤버 가입 sub = {}",sub);
                    return createMemberService.createMember(sub);
                });

        AuthToken authToken = jwtProvider.generateAuthToken(memberId, Role.ROLE_USER);
        createRefreshTokenService.createRefreshToken(memberId, authToken.refreshToken());
        return SignUpResponse.from(authToken);
    }

    private MultiValueMap<String, String> buildTokenRequestMap(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", "184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com");
        map.add("redirect_uri", "https://www.ddeng-gu.shop/api/v1/login/oauth/signup");
        map.add("client_secret", "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");

        return map;
    }


}
