package hello.todo.domain.routine.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.infra.exception.RoutineNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor
public class RemoveRoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public void removeRoutine(Long memberId,Long deleteRoutineId){
        Routine routine = routineRepository.findRoutineByIdAndMemberId(deleteRoutineId,memberId)
                .orElseThrow(() -> new RoutineNotFoundException("존재하지 않는 루틴입니다."));
        routineRepository.delete(routine);
    }
}
