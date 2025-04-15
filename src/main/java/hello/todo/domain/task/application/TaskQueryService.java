package hello.todo.domain.task.application;

import hello.todo.domain.task.domain.TaskRepository;
import hello.todo.domain.task.presentation.dto.response.TaskSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskQueryService {

    private final TaskRepository taskRepository;

    //날짜별 일정 간단조회
    public List<TaskSummaryResponse> getTaskSummaryListByMonth(long memberId,int year, int month) {
        return taskRepository.findTaskByMonth(memberId,year,month);
    }




}
