package hello.todo.domain.task.domain;
import hello.todo.domain.task.presentation.dto.response.TaskSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("""
    SELECT new hello.todo.domain.task.presentation.dto.response.TaskSummaryResponse(t.memberId,t.name,t.taskDate) FROM Task t 
    WHERE t.memberId = :memberId  
    AND YEAR(t.taskDate) = :year 
    AND MONTH(t.taskDate) = :month
    """)
    List<TaskSummaryResponse> findTaskByMonth(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);
}
