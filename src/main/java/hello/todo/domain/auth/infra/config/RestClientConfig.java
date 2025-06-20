package hello.todo.domain.auth.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean(value = "googleCodeClient")
    public RestClient googleCodeRestClient() {
        return RestClient.builder()
                .baseUrl("https://oauth2.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    //TODO : 레스트 클라이언트 생성, 에세스토큰으로 유저 이름, 이메일 받아오는 클라이언트
    @Bean(value = "googleAccessTokenClient")
    public RestClient googleAccessTokenClient() {
        return RestClient.builder()
                .baseUrl("https://www.googleapis.com")
                .build();
    }
}
