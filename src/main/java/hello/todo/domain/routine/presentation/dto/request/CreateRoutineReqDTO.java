package hello.todo.domain.routine.presentation.dto.request;

import hello.todo.domain.routine.domain.Cycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateRoutineReqDTO(
        @NotBlank
        String name,

        @NotNull
        List<Cycle> cycles,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate
) { }
