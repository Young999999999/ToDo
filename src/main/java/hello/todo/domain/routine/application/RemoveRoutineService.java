package hello.todo.domain.routine.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RemoveRoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public void removeRoutine(Long memberId,Long deleteRoutineId){
        Routine routine = routineRepository.findRoutineByIdAndMemberId(deleteRoutineId,memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));
        routineRepository.delete(routine);
    }
}
