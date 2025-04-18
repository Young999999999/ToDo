package hello.todo.domain.auth.presentation;

import hello.todo.domain.auth.application.GoogleOAuthService;
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

    @GetMapping("/oauth/url")
    public ResponseEntity<Void> redirectGoogleLoginUrl(){
        String redirectUrl = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&access_type=offline&redirect_uri=https://www.ddeng-gu.shop/api/v1/login/oauth/signup&client_id=184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com";

        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT) //
                .location(URI.create(redirectUrl))
                .build();
    }


    @GetMapping("/oauth/signup")
    public void googleLogin(@RequestParam("code") String code){
        googleOAuthService.signUp(code);
    }


}
