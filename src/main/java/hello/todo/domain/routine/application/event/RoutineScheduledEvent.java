package hello.todo.domain.routine.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoutineScheduledEvent {
    Long memberId;
    Long routineId;
    String title;
    LocalDate date;
}
