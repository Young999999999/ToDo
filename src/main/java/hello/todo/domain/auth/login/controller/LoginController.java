package hello.todo.domain.auth.login.controller;

import hello.todo.domain.auth.login.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {

    private final GoogleOAuthService googleOAuthService;
//    @GetMapping("/oauth/url")
//    public String sendGoogleLoginURL(){
//        //리다이렉트 url 수정필요
//        return "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&access_type=offline&redirect_uri=http://localhost:8080/api/v1/oauth/signup?type=GOOGLE&client_id=184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com";
//    }


    @GetMapping("/oauth/url")
    public ResponseEntity<Void> redirectGoogleLoginUrl(){
        String redirectUrl = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&access_type=offline&redirect_uri=http://localhost:8080/api/v1/login/oauth&client_id=184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com";

        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT) //
                .location(URI.create(redirectUrl))
                .build();
    }


    @GetMapping("/oauth")
    public void googleLogin(@RequestParam String code){
        System.out.println(code);
        //googleOAuthService.signupAndGetJwtTokens(code);
    }


}
