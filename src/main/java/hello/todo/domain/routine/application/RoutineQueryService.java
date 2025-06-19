package hello.todo.domain.routine.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.presentation.dto.response.GetRoutineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineQueryService {

    private final RoutineRepository routineRepository;

    //멤버의 루틴을 전체 조회한다.
    public List<GetRoutineResponse> findAllRoutine(Long memberId) {
        return routineRepository.findAllByMemberId(memberId)
                .stream()
                .map(GetRoutineResponse::from)
                .toList();
    }

    //멤버의 루틴을 하나 조회한다.
    public GetRoutineResponse findRoutine(Long routineId, Long memberId) {
        Routine routine = routineRepository.findRoutineByIdAndMemberId(routineId, memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));
        return GetRoutineResponse.from(routine);
    }

    //루틴이 존재하는지 검증하고 조회한다.
    public Routine findExistingRoutine(Long routineId, Long memberId) {
        return routineRepository.findRoutineByIdAndMemberId(routineId, memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));
    }
}
