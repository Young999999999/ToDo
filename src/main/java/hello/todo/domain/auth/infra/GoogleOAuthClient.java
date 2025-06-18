package hello.todo.domain.auth.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.todo.domain.auth.application.OAuthClient;
import hello.todo.domain.auth.infra.dto.response.GoogleAccessTokenResDTO;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleOAuthClient implements OAuthClient {

    private final ObjectMapper jacksonObjectMapper;
    private final RestClient googleRestClient;

    @Override
    public String exchangeCodeToSub(MultiValueMap<String, String> map) {
        log.info("구글에 accessToken 발급 요청 code: {}",map.get("code"));
        GoogleAccessTokenResDTO googleAccessToken = requestAccessTokenFromGoogle(map);
        log.info("구글에 accessToken 발급 완료 code: {}",map.get("code"));
        String idToken = googleAccessToken.id_token();
        return extractSub(idToken);
    }

    private String extractSub(String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            String payload = parts[1];
            String json = new String(Base64.getUrlDecoder().decode(payload));
            JsonNode node = jacksonObjectMapper.readTree(json);

            return node.get("sub").asText();
        } catch (JsonProcessingException e) {
            log.info("제이슨 파싱 에러 발생");
            throw new CustomException(ErrorCode.INVALID_ID_TOKEN);
        }

    }

    private GoogleAccessTokenResDTO requestAccessTokenFromGoogle(MultiValueMap<String, String> map) {

        return googleRestClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    log.info(res.getStatusText());
                    throw new CustomException(ErrorCode.GOOGLE_4XX);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new CustomException(ErrorCode.GOOGLE_5XX);
                })
                .body(GoogleAccessTokenResDTO.class);
    }
}
