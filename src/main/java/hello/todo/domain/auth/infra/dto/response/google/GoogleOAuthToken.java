package hello.todo.domain.auth.infra.dto.response.google;

import hello.todo.domain.auth.infra.dto.response.OAuthToken;
import lombok.Data;

@Data
public class GoogleOAuthToken extends OAuthToken {
    private String expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
}
