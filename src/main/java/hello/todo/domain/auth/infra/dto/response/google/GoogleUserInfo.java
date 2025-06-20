package hello.todo.domain.auth.infra.dto.response.google;

import hello.todo.domain.auth.infra.dto.response.OAuthUserInfo;
import lombok.Data;

@Data
public class GoogleUserInfo extends OAuthUserInfo {
    private String id;
    private boolean verified_email;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
