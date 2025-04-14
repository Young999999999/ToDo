package hello.todo.domain.auth.login.service.dto.response;

public record GoogleAccessTokenResDTO(
        String access_token,
        String expires_in,
        String refresh_token,
        String id_token,
        String scope,
        String token_type
) {
}
