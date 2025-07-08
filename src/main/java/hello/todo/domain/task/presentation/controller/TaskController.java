package hello.todo.domain.task.presentation.controller;

import hello.todo.domain.common.security.JwtUserDetails;
import hello.todo.domain.task.application.CreateTaskService;
import hello.todo.domain.task.application.TaskQueryService;
import hello.todo.domain.task.presentation.dto.request.CreateTaskRequest;
import hello.todo.domain.task.presentation.dto.request.TaskSummaryRequest;
import hello.todo.domain.task.presentation.dto.response.TaskSummaryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskQueryService taskQueryService;
    private final CreateTaskService createTaskService;
    //content 수정하기

    //content 조회하기

    //일정 만들기
    @PostMapping
    public void createTask(
            @AuthenticationPrincipal JwtUserDetails user,
            @RequestBody CreateTaskRequest req
    ) {
        createTaskService.create(user.getUserId(), req.toCommand());
    }

    //연도, 월별 일정 조회하기
    @GetMapping("/summary")
    List<TaskSummaryResponse> getTaskSummaryList(@RequestParam @Valid TaskSummaryRequest req) {
        return taskQueryService.getTaskSummaryListByMonth(1L, req.month(), req.year());
    }

}
