package hello.todo.domain.common.security;

import hello.todo.domain.common.jwt.JwtUtil;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.member.domain.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 로그인 관련 요청은 JWT 검증을 하지 않음
        String requestPath = request.getRequestURI();
        log.info(requestPath);
        if (requestPath.startsWith("/api/v1/login")||requestPath.startsWith("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        //JWT 관련 에러를 처리하기 위함.
        try {
            String token = getTokenAndValidateJwtRequest(request);
            Role role = jwtUtil.getRoleFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);

            log.info("userId = {}, role = {} ", userId.toString(), role.name());
            Authentication authentication = jwtAuthenticationProvider.authenticate(userId, role);
            SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.getContextHolderStrategy().setContext(securityContext);
        } catch (CustomException e){
            request.setAttribute("errorCode", e.getErrorCode());
        }

        //다음 필터로 넘어가기 위해 필수이다.
        filterChain.doFilter(request, response);
    }

    private String getTokenAndValidateJwtRequest(HttpServletRequest request) {
        String jwtHeader = request.getHeader("Authorization");

        if(!StringUtils.hasText(jwtHeader) || !jwtHeader.startsWith("Bearer ")) {
            throw  new CustomException(ErrorCode.JWT_INVALID);
        }

        return jwtHeader.replace("Bearer ","");
    }


}
