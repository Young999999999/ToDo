package hello.todo.domain.routine.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineReqDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CreateRoutineService {

    private final RoutineRepository routineRepository;
    
    @Transactional
    public void createRoutine(CreateRoutineReqDTO dto, Long memberId){
        Routine routine = Routine.of(memberId, dto.name(),dto.days(),dto.startDate(),dto.endDate());
        routineRepository.save(routine);
    }
}
