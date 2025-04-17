package hello.todo.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER-001","사용자가 존재하지 않습니다."),

    //ROUTINE
    ROUTINE_NOT_FOUND(HttpStatus.NOT_FOUND,"ROUTINE-001","루틴이 존재하지 않습니다."),

    //Other Error
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UNKNOWN-001","알 수 없는 에러입니다."),

    //AUTH
    JWT_INVALID(HttpStatus.UNAUTHORIZED,"AUTH-001","토큰의 형식이 알맞지 않습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED,"AUTH-002","만료된 토큰입니다."),
    JWT_NOT_PROVIDED(HttpStatus.UNAUTHORIZED,"AUTH-003","토큰이 입력되지 않았습니다"),
    GOOGLE_4XX(HttpStatus.UNAUTHORIZED,"AUTH-004","해당 서비스가 구글과 연결중에 문제가 발생했습니다."),
    GOOGLE_5XX(HttpStatus.INTERNAL_SERVER_ERROR,"AUTH-005","해당 서비스가 구글과 연결중에 문제가 발생했습니다."),
    INVALID_ID_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR,"AUTH-006","유효하지않은 ID Token 입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN,"AUTH-007","권한이 없습니다.");

    private final HttpStatus httpStatus;	// 에러 응답 상태 (404)
    private final String code;				// 에러 코드 (ACCOUNT-001)
    private final String message;           // 에러 메세지 ("사용자가 존재하지 않음")
}
