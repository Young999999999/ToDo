package hello.todo.domain.member.presentation.dto.request;

import hello.todo.domain.member.domain.Routine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

import static hello.todo.domain.member.domain.Routine.*;

public record CreateRoutineReqDTO(
        @NotBlank
        String name,

        @NotNull
        Cycle cycle,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate
) { }
