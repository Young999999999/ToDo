package hello.todo.domain.task.application;

import hello.todo.domain.task.application.command.CreateTaskCommand;
import hello.todo.domain.task.domain.Task;
import hello.todo.domain.task.domain.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateTaskService {

    private final TaskRepository taskRepository;

    public void create(Long memberId, CreateTaskCommand command) {
        Task task = Task.of(memberId, command.title(), LocalDate.now(), Task.Type.NORMAL);
        taskRepository.save(task);
    }
}
