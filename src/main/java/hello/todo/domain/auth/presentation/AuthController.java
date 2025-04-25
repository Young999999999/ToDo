package hello.todo.domain.auth.presentation;

import hello.todo.domain.auth.application.GoogleOAuthService;
import hello.todo.domain.auth.application.ReIssueRefershTokenService;
import hello.todo.domain.auth.presentation.dto.response.SignUpResponse;
import hello.todo.domain.auth.presentation.dto.response.TokenReIssueResponse;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.common.jwt.dto.AuthToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOAuthService googleOAuthService;
    private final ReIssueRefershTokenService reIssueRefershTokenService;

    //google OAuth 로그인 form을 얻기 위한 메서드. 사용자를 리다이렉션함.
    @GetMapping("/oauth/url")
    public ResponseEntity<Void> redirectGoogleLoginUrl() {
        String redirectUrl = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&access_type=offline&redirect_uri=https://www.ddeng-gu.shop/api/v1/login/oauth/signup&client_id=184642286173-vkd43ig36jr6ui7e4a5r01mtb81ehdo1.apps.googleusercontent.com";

        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT) //
                .location(URI.create(redirectUrl))
                .build();
    }

    //google OAuth 회원가입을 위한 메서드. 인증을 시도하고 토큰을 발급함.
    @GetMapping("/oauth/signup")
    public SignUpResponse googleLogin(@RequestParam("code") String code) {
        AuthToken authToken = googleOAuthService.signUp(code);
        return SignUpResponse.from(authToken);
    }

    //리프레시 토큰을 사용해 인증 토큰 재발급을 위한 메서드.
    @GetMapping("/reissue")
    public TokenReIssueResponse reissue(HttpServletRequest request) {
        String refreshToken = getTokenAndValidateRequest(request);
        AuthToken authToken = reIssueRefershTokenService.reIssueAuthToken(refreshToken);
        return TokenReIssueResponse.from(authToken);
    }

    private String getTokenAndValidateRequest(HttpServletRequest request ) {
        String jwtHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(jwtHeader) || !jwtHeader.startsWith("Bearer ")) {
            throw  new CustomException(ErrorCode.JWT_INVALID);
        }
        return jwtHeader.replace("Bearer ","");
    }
}
