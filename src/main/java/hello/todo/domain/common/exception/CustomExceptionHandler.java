package hello.todo.domain.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class CustomExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e){
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }


    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(Exception e){
        return ErrorResponseEntity.toResponseEntity(ErrorCode.UNKNOWN_ERROR);
    }





}
