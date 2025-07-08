package hello.todo.domain.task.application;

import hello.todo.domain.routine.application.event.RoutineCreatedEvent;
import hello.todo.domain.routine.application.event.RoutineUpdatedEvent;
import hello.todo.domain.task.domain.Task;
import hello.todo.domain.task.domain.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskEventHandler {

    private final TaskRepository taskRepository;

    //TODO: 공통 로직이 중복된다. 이벤트를 하나로 합치는 것은 이벤트가 명확하지 않으므로 안좋아보인다. 인터페이스, 상속을 활용하면 좋아 보이긴한다.
    @EventListener
    public void createTaskFromCreate(RoutineCreatedEvent event) {
        Task task = Task.of(event.getMemberId(), event.getRoutineId(), event.getTitle(), event.getDate(), Task.Type.ROUTINE);
        taskRepository.save(task);
    }

    @EventListener
    public void createTaskFromUpdate(RoutineUpdatedEvent event) {
        Task task = Task.of(event.getMemberId(), event.getRoutineId(), event.getTitle(), event.getDate(), Task.Type.ROUTINE);
        taskRepository.save(task);
    }

    @EventListener
    public void createTaskFromScheduler(RoutineUpdatedEvent event) {
        Task task = Task.of(event.getMemberId(), event.getRoutineId(), event.getTitle(), event.getDate(), Task.Type.ROUTINE);
        taskRepository.save(task);
    }
}
