package hello.todo.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.todo.domain.auth.service.dto.response.GoogleAccessTokenResDTO;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final RestClient googleCodeClient;
    private final ObjectMapper jacksonObjectMapper;

    public void signUp(String code) {
        MultiValueMap<String, String> map = buildTokenRequestMap(code);
        String sub = exchangeCodeToSub(map);

        //TODO: sub 기반 회원가입 구현.
    }

    private MultiValueMap<String, String> buildTokenRequestMap(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", "184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com");
        map.add("redirect_uri", "https://www.ddeng-gu.shop/api/v1/login/oauth/signup");
        map.add("client_secret", "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");

        return map;
    }

    private String exchangeCodeToSub(MultiValueMap<String, String> map) {
        GoogleAccessTokenResDTO googleAccessToken = requestAccessTokenFromGoogle(map);
        String idToken = googleAccessToken.id_token();
        log.info("IdToken:" + idToken);
        String sub = extractSub(idToken);
        log.info("Sub:" + sub);

        return sub;
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
            log.info("Google 토큰 발급 받기 시작");
            return googleCodeClient.post()
                    .uri("/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(map)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new CustomException(ErrorCode.GOOGLE_4XX);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new CustomException(ErrorCode.GOOGLE_5XX);
                    })
                    .body(GoogleAccessTokenResDTO.class);
    }



}
