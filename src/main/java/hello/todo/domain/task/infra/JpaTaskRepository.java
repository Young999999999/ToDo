package hello.todo.domain.task.infra;

import hello.todo.domain.task.domain.Task;
import hello.todo.domain.task.domain.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends TaskRepository, JpaRepository<Task, Long> {
}
