package hello.todo.domain.auth.application;

import hello.todo.domain.auth.infra.dto.response.OAuthToken;
import hello.todo.domain.auth.infra.dto.response.OAuthUserInfo;

public interface OAuthClient {

    OAuthToken exchangeCodeToOAuthToken(String code);

    OAuthUserInfo exchangeAccessTokenToUserInfo(String accessToken);
}
