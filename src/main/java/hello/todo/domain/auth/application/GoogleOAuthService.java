package hello.todo.domain.auth.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.todo.domain.auth.infra.dto.response.OAuthToken;
import hello.todo.domain.auth.infra.dto.response.OAuthUserInfo;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.common.jwt.JwtProvider;
import hello.todo.domain.common.jwt.dto.AuthToken;
import hello.todo.domain.member.application.CreateMemberService;
import hello.todo.domain.member.application.MemberQueryService;
import hello.todo.domain.member.application.command.CreateMemberCommand;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoogleOAuthService {

    private final ObjectMapper jacksonObjectMapper;
    private final OAuthClient oAuthClient;
    private final CreateMemberService createMemberService;
    private final MemberQueryService memberQueryService;
    private final JwtProvider jwtProvider;
    private final RegistRefreshTokenService registRefreshTokenService;

    /**
     * 구글 소셜 로그인 기능을 수행한다.
     * 기존 회원이라면 애세스 토큰, 리프레쉬 토큰을 발급한다.
     * 기존 회원이 아니라면 구글의 이메일, 이름, sub 기반으로 회원가입을 진행한다.
     */
    public AuthToken signUp(String code) {
        OAuthToken oAuthToken = oAuthClient.exchangeCodeToOAuthToken(code);
        //IdToken으로부터 sub를 추출한다.
        String sub = extractSubFromIdToken(oAuthToken.getId_token());

        //기존의 회원이 존재하지 않는다면 구글 sub 기반으로 신규 회원 가입을 진행한다.
        Member member = memberQueryService.findMemberBySub(sub)
                .orElseGet(() -> {
                    OAuthUserInfo userInfo = oAuthClient.exchangeAccessTokenToUserInfo(oAuthToken.getAccess_token());
                    CreateMemberCommand command = CreateMemberCommand.of(sub, userInfo.getEmail(), userInfo.getName());
                    log.info("사용자가 회원가입 했습니다. 멤버 = {}", command);
                    return createMemberService.createMember(command);
                });

        //애세스토큰, 리프레시토큰을 생성한다. 리프레시토큰은 DB에 저장한다.
        AuthToken authToken = jwtProvider.generateAuthToken(member.getId(), member.getRole());
        registRefreshTokenService.regist(member.getId(), authToken.refreshToken());
        return authToken;
    }

    /**
     * 구글의 idToken으로부터 유저의 sub를 추출한다.
     */
    private String extractSubFromIdToken(String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            String payload = parts[1];
            String json = new String(Base64.getUrlDecoder().decode(payload));
            JsonNode node = jacksonObjectMapper.readTree(json);

            return node.get("sub").asText();
        } catch (JsonProcessingException e) {
            log.error("제이슨 파싱 에러 발생");
            throw new CustomException(ErrorCode.INVALID_ID_TOKEN);
        }

    }
}
