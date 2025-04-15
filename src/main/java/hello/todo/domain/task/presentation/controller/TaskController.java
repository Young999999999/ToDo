package hello.todo.domain.task.presentation.controller;

import hello.todo.domain.task.application.TaskQueryService;
import hello.todo.domain.task.presentation.dto.request.TaskSummaryRequest;
import hello.todo.domain.task.presentation.dto.response.TaskSummaryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskQueryService taskQueryService;

    //content 수정하기

    //content 조회하기

    //연도, 월별 일정 조회하기
    @GetMapping("/task/summary")
    List<TaskSummaryResponse> getTaskSummaryList(@RequestParam @Valid TaskSummaryRequest req){
        return taskQueryService.getTaskSummaryListByMonth(1L,req.month(),req.year());
    }

}
