package hello.todo.domain.common.jwt.dto;

public record AuthToken(
        String accessToken,
        String refreshToken
) {
    public static AuthToken of(String accessToken, String refreshToken) {
        return new AuthToken(accessToken, refreshToken);
    }
}
