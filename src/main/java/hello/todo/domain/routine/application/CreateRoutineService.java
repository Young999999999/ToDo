package hello.todo.domain.routine.application;

import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateRoutineService {

    private final RoutineRepository routineRepository;
    
    @Transactional
    public void createRoutine(CreateRoutineRequest dto, Long memberId){
        Routine routine = Routine.of(memberId, dto.name(),dto.days(),dto.startDate(),dto.endDate());
        routineRepository.save(routine);
    }
}
