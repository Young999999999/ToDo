package hello.todo.domain.auth.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//로컬환경의 웹브라우저에서 테스트를 위함. 운영 단계에선 삭제해야함
@RestController
public class FaviconController {
    @GetMapping("/favicon.ico")
    public void returnNoFavicon() {
        // 아무 것도 반환 안 함
    }
}