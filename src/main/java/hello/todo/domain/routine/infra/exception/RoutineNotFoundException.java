package hello.todo.domain.routine.infra.exception;

import hello.todo.common.exception.NotFoundException;

public class RoutineNotFoundException extends NotFoundException {
    public RoutineNotFoundException(String message) {
        super(message);
    }
}
