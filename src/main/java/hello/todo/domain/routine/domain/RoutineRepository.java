package hello.todo.domain.routine.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine,Long> {

    Optional<Routine> findRoutineByIdAndMemberId(Long routineId, Long memberId);

    List<Routine> findAllByMemberId(Long memberId);

    void delete(Routine routine);
}
