package hello.todo.domain.routine.presentation.dto.response;

import hello.todo.domain.routine.domain.Day;
import hello.todo.domain.routine.domain.Routine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public record RoutineDetailResponse(
        long routine_id,
        String name,
        Set<Day> days,
        LocalDate startDate,
        LocalDate endDate
)
{
    public static RoutineDetailResponse from (Routine routine){
        return new RoutineDetailResponse(routine.getId(),routine.getName(),routine.getDays(),routine.getStartDate(),routine.getEndDate());
    }

}
