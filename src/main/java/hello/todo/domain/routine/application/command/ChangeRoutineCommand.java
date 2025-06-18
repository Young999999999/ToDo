package hello.todo.domain.routine.application.command;

import hello.todo.domain.routine.domain.Day;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public record ChangeRoutineCommand(
        Optional<String> name,
        Optional<Set<Day>> days,
        Optional<LocalDate> endDate
) {
}
