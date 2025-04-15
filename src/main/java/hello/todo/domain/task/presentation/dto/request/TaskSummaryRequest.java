package hello.todo.domain.task.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TaskSummaryRequest(
        @Min(2025) @Max(2030)
        int year,

        @Min(1) @Max(12)
        int month
) { }
