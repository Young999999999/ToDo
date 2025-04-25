package hello.todo.domain.auth.presentation.dto.response;

import hello.todo.domain.common.jwt.dto.AuthToken;

public record TokenReIssueResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenReIssueResponse from(AuthToken authToken) {
        return new TokenReIssueResponse(authToken.accessToken(),authToken.refreshToken());
    }
}
