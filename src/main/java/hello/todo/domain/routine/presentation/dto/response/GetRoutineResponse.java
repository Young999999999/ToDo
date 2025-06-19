package hello.todo.domain.routine.presentation.dto.response;

import hello.todo.domain.routine.domain.Day;
import hello.todo.domain.routine.domain.Routine;

import java.time.LocalDate;
import java.util.Set;

public record GetRoutineResponse(
        Long routineId,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Set<Day> days
) {
    public static GetRoutineResponse from(Routine routine) {
        return new GetRoutineResponse(
                routine.getId(),
                routine.getName(),
                routine.getStartDate(),
                routine.getEndDate(),
                routine.getDays()
        );
    }
}
