package hello.todo.domain.auth.infra;

import hello.todo.domain.auth.application.OAuthClient;
import hello.todo.domain.auth.infra.dto.response.OAuthToken;
import hello.todo.domain.auth.infra.dto.response.google.GoogleOAuthToken;
import hello.todo.domain.auth.infra.dto.response.google.GoogleUserInfo;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class GoogleOAuthClient implements OAuthClient {

    @Qualifier("googleCodeClient")
    @Autowired
    private RestClient googleCodeClient;

    @Qualifier("googleAccessTokenClient")
    @Autowired
    private RestClient googleAccessTokenClient;

    @Override
    public OAuthToken exchangeCodeToOAuthToken(String code) {
        log.info("구글에 OAuthToken 발급 요청 code: {}", code);
        OAuthToken googleOAuthToken = requestOAuthTokenFromGoogle(buildTokenRequestMap(code));
        log.info("구글에 OAuthToken 발급 완료 accessToken: {}", googleOAuthToken.getAccess_token());
        return googleOAuthToken;
    }

    @Override
    public GoogleUserInfo exchangeAccessTokenToUserInfo(String accessToken) {
        return requestUserInfoFromGoogle(accessToken);
    }

    //OAuthToken을 받아오기 위해 정해진 Api 스펙대로 request body를 작성한다.
    private MultiValueMap<String, String> buildTokenRequestMap(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", "184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com");
        //map.add("redirect_uri", "https://www.ddeng-gu.shop/api/v1/login/oauth/signup");
        map.add("redirect_uri", "http://localhost:8080/api/v1/login/oauth/signup");
        map.add("client_secret", "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");
        return map;
    }

    /**
     * Google에 사용자의 accessToken을 기반으로 사용자 정보를 요청한다.
     */
    private GoogleUserInfo requestUserInfoFromGoogle(String accessToken) {
        return googleAccessTokenClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", accessToken)
                        .build())
                .retrieve()
                .body(GoogleUserInfo.class);
    }

    /**
     * Google에 OAuthToken을 요청한다. OAuthToken에는 accessToken, idToken이 존재한다.
     */
    private OAuthToken requestOAuthTokenFromGoogle(MultiValueMap<String, String> map) {

        return googleCodeClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new CustomException(ErrorCode.GOOGLE_4XX);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new CustomException(ErrorCode.GOOGLE_5XX);
                })
                .body(GoogleOAuthToken.class);
    }
}
