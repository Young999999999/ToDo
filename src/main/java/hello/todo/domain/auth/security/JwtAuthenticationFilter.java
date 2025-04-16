package hello.todo.domain.auth.security;

import hello.todo.domain.auth.jwt.JwtUtil;
import hello.todo.domain.member.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
import java.io.IOException;

import static hello.todo.domain.member.domain.Member.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String path = request.getServletPath();
            if (List.of(SecurityConfig.WHITE_LIST).contains(path)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = getTokenAndValidateJwtRequest(request);
            Role role = jwtUtil.getRoleFromToken(token)
                    .orElse(Role.ROLE_FREE);
            Long userId = jwtUtil.getUserIdFromToken(token);

            log.info("userId = {}, role = {} ",userId.toString(),role.name());
            Authentication authentication =  jwtAuthenticationProvider.authenticate(userId,role);
            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);

        } catch (CustomExpiredJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.EXPIRED_TOKEN);
        } catch (EmptyJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.TOKEN_CAN_NOT_BE_NULL);
        } catch (InvalidJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.TOKEN_NOT_VALID);
        } catch (InvalidAuthTypeException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.AUTHTYPE_NOT_VALID);
        }

        //다음 필터로 넘어가기 위해 필수이다.
        filterChain.doFilter(request, response);
    }

    private String getTokenAndValidateJwtRequest(HttpServletRequest request) {

        String jwtHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(jwtHeader)) {
            throw new EmptyJwtException("token is not provided");
        }

        if (!jwtHeader.startsWith("Bearer ")) {
            throw new InvalidAuthTypeException("Bearer is not provided");
        }

        return jwtHeader.replace("Bearer ","");
    }


}
