package hello.todo.domain.auth.infra.dto.response;

import lombok.Getter;

@Getter
public abstract class OAuthUserInfo {
    private String name;
    private String email;
}
