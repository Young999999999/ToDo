package hello.todo.domain.routine.domain;

import java.util.Optional;

public interface RoutineRepository {

    Optional<Routine> findRoutineByIdAndMemberId(Long routineId, Long memberId);

    Routine save(Routine routine);

    void delete(Routine routine);
}
