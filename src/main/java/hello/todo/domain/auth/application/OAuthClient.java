package hello.todo.domain.auth.application;

import org.springframework.util.MultiValueMap;

public interface OAuthClient {

    String exchangeCodeToSub(MultiValueMap<String, String> map);
}
