package hello.todo.domain.member.domain.exception;

import hello.todo.common.exception.NotFoundException;

public class CycleNotFoundException extends NotFoundException {
    public CycleNotFoundException(String message) {
        super(message);
    }
}
