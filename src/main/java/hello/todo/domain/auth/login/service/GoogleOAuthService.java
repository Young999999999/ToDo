package hello.todo.domain.auth.login.service;

import hello.todo.domain.auth.login.service.dto.response.GoogleAccessTokenResDTO;
import hello.todo.domain.auth.login.service.dto.response.GoogleUserInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class GoogleOAuthService {

    private final RestClient googleCodeClient;
    private final RestClient googleTokenClient;

    public GoogleOAuthService(
            @Qualifier("googleCodeClient") RestClient googleCodeClient,
            @Qualifier("googleTokenClient") RestClient googleTokenClient
    ) {
        this.googleCodeClient = googleCodeClient;
        this.googleTokenClient = googleTokenClient;
    }

//    public void signupAndGetJwtTokens(String code) {
//        MultiValueMap<String, String> map = getMultiValueMap(code);
//        UserInfoDTO userInfo = getUserInfo(map,type);
//        Optional<Parent> optionalParent = userRepository.findParentBySocialIdAndLoginType(userInfo.getSocialId(), type);
//
//        if(optionalParent.isPresent()) {
//            Parent parent = optionalParent.get();
//            return jwtService.generateJwtToken(parent);
//        } else {
//            //refator 필요
//            Parent parent = new Parent(userInfo.getEmail(), Parent.MembershipType.FREE,Role.ROLE_FREE,type, userInfo.getSocialId());
//            userRepository.save(parent);
//            return jwtService.generateJwtToken(parent);
//        }
//    }

    private MultiValueMap<String, String> getMultiValueMap(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", "184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com");
        map.add("redirect_uri", "http://localhost:8080/api/v1/oauth");
        map.add("client_secret", "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");

        return map;
    }


    private void  getUserInfo(MultiValueMap<String, String> map) {

        //google로부터 에세스 토큰 가져옴
        GoogleAccessTokenResDTO googleAccessToken = requestAccessTokenFromGoogle(map);

        googleAccessToken.id_token();
        //에세스 토큰으로부터 구글 계정 정보 가져옴
        //GoogleUserInfoResDTO googleUserInfo = getUserInfoFromGoogle(googleAccessToken.access_token());

    }

    private GoogleAccessTokenResDTO requestAccessTokenFromGoogle(MultiValueMap<String, String> map) {

            return googleCodeClient.post()
                    .uri("/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(map)
                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        throw new GoogleOauthException("인가 코드 받기 4xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.");
//                    })
//                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
//                        throw new GoogleOauthException("인가 코드 받기 5xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.");
//                    })
                    .body(GoogleAccessTokenResDTO.class);

    }

    private GoogleUserInfoResDTO getUserInfoFromGoogle(String token) {

            return googleTokenClient.get()
                    .uri("/oauth2/v1/userinfo")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        throw new GoogleOauthException("사용자 정보 가져오기 4xx 오류, 해당 서비스가 Google과 연결 중 문제가 발생했습니다.");
//                    })
//                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
//                        throw new GoogleOauthException("사용자 정보 가져오기 5xx 오류, 해당 서비스가 Google과 연결 중 문제가 발생했습니다.");
//                    })
                    .body(GoogleUserInfoResDTO.class);

    }




}
