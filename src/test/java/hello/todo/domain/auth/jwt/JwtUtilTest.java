package hello.todo.domain.auth.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil("test-secret-key-qkrdudrbgkswldmswhgdkgka");

    @Test
    @DisplayName("Jwt 에세스 토큰을 생성한다.")
    void generateAccessToken() {
        //given
        Long userId = 1L;

        //when
        String token = jwtUtil.generateAccessToken(userId);

        //then
        assertNotNull(token);
    }

    @Test
    @DisplayName("Jwt 리프레쉬 토큰을 생성한다.")
    void generateRefreshToken() {
        //given
        Long userId = 1L;

        //when
        String token = jwtUtil.generateRefreshToken(userId);

        //then
        assertNotNull(token);
    }

    @Test
    @DisplayName("Jwt 토큰의 만료 시간이 지났는지 검증한다.")
    void validateToken() {
        //given
        Long userId = 1L;
        String token = jwtUtil.generateAccessToken(userId);

        //when
        boolean result = jwtUtil.validateToken(token);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Jwt 토큰에서 유저ID를 가져온다.")
    void getUserIdFromToken() {
        //given
        Long userId = 1L;
        String token = jwtUtil.generateAccessToken(userId);

        //when
        Long extractUserId = jwtUtil.getUserIdFromToken(token);

        //then
        assertEquals(userId, extractUserId);
    }
}