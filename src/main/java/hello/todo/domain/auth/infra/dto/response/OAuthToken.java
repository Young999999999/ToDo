package hello.todo.domain.auth.infra.dto.response;

import lombok.Getter;

@Getter
public abstract class OAuthToken {
    private String access_token;
    private String id_token;

}
