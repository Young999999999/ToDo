package hello.todo.domain.auth.presentation.dto.response;

import hello.todo.domain.common.jwt.dto.AuthToken;

public record SignUpResponse(
        String accessToken,
        String refreshToken
) {
    public static SignUpResponse from(AuthToken authToken) {
        return new SignUpResponse(authToken.accessToken(),authToken.refreshToken());
    }
}
