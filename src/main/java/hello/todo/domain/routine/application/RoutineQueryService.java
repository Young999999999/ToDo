package hello.todo.domain.routine.application;

import hello.todo.common.exception.NotFoundException;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import hello.todo.domain.routine.presentation.dto.response.RoutineDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoutineQueryService {

    private final RoutineRepository routineRepository;

    //루틴 상세조회
    @Transactional(readOnly = true)
    public RoutineDetailResponse getRoutineDetail(long routineId, long memberId){
        Routine routine = routineRepository.findRoutineByIdAndMemberId(routineId,memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 루틴입니다."));

        return RoutineDetailResponse.from(routine);
    }






}
