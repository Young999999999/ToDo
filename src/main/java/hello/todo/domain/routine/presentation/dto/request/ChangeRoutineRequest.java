package hello.todo.domain.routine.presentation.dto.request;

import hello.todo.domain.routine.application.command.ChangeRoutineCommand;
import hello.todo.domain.routine.domain.Day;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public record ChangeRoutineRequest(
        String name,
        Set<Day> days,
        LocalDate endDate
) {
    public ChangeRoutineCommand toChangeRoutineCommand(){
        return new ChangeRoutineCommand(Optional.ofNullable(name),Optional.ofNullable(days),Optional.ofNullable(endDate));
    }
}
