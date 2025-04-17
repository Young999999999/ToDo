package hello.todo.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class ErrorResponseEntity {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.of(e));
    }

    public static ErrorResponseEntity of(ErrorCode e) {
        return new ErrorResponseEntity(e.getHttpStatus().value(), e.name(), e.getCode(), e.getMessage());
    }

}