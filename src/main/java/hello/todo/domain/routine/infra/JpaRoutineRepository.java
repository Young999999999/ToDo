package hello.todo.domain.routine.infra;

import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("routineRepository")
public interface JpaRoutineRepository extends JpaRepository<Routine,Long>, RoutineRepository {

    Routine findRoutineByIdAndAndMemberId(Long routineId,Long MemberId);
    Routine findRoutineById(Long routineId);
    Routine save(Routine routine);
    void delete(Routine routine);

}
