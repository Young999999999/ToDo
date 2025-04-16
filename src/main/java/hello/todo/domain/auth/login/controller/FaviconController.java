package hello.todo.domain.auth.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//로컬환경의 웹브라우저에서 테스트를 위함. 운영 단계에선 삭제해야함
@RestController
public class FaviconController {
    @RequestMapping("/favicon.ico")
    public void returnNoFavicon() {
        // 아무 것도 반환 안 함
    }
}