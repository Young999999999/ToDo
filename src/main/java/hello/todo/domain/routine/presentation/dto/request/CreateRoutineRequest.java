package hello.todo.domain.routine.presentation.dto.request;

import hello.todo.domain.routine.domain.Day;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateRoutineRequest(
        @NotBlank
        String name,

        @NotNull
        Set<Day> days,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate
) { }
