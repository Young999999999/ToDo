package hello.todo.domain.auth.jwt.exception;

public class CustomExpiredJwtException extends RuntimeException {
    public CustomExpiredJwtException(String message) {
        super(message);
    }
}
