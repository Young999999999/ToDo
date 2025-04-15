package hello.todo.domain.auth.jwt.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message) {super(message);}
}
