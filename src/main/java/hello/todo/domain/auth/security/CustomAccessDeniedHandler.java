package hello.todo.domain.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.common.exception.ErrorResponseEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.of(ErrorCode.ACCESS_DENIED);
        response.setStatus(errorResponse.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
