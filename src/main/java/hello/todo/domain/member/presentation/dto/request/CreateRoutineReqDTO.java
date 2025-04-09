package hello.todo.domain.member.presentation.dto.request;

import hello.todo.domain.member.domain.Cycle;
import hello.todo.domain.member.domain.Routine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

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
