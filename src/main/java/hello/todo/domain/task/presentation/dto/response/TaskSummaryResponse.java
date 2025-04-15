package hello.todo.domain.task.presentation.dto.response;

import java.time.LocalDate;

public record TaskSummaryResponse(
        Long taskId,
        String name,
        LocalDate taskDate

) {
    public static TaskSummaryResponse from(Long taskId, String name, LocalDate taskDate){
        return new TaskSummaryResponse(taskId, name, taskDate);
    }
}
