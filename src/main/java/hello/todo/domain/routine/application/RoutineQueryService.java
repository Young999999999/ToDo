package hello.todo.domain.routine.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.presentation.dto.response.RoutineDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineQueryService {

    private final RoutineRepository routineRepository;

    //루틴 상세조회
    public RoutineDetailResponse getRoutineDetail(long routineId, long memberId) {
        Routine routine = routineRepository.findRoutineByIdAndMemberId(routineId, memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));

        return RoutineDetailResponse.from(routine);
    }

    public Routine findExistingRoutine(Long routineId, Long memberId) {
        return routineRepository.findRoutineByIdAndMemberId(routineId, memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTINE_NOT_FOUND));
    }
}
