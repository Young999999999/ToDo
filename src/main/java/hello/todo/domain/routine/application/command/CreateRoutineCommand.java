package hello.todo.domain.routine.application.command;

import hello.todo.domain.routine.domain.Day;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Set;

public record CreateRoutineCommand(
   String routineName,
   LocalDate startDate,
   LocalDate endDate,
   Set<Day> days
) {
}
