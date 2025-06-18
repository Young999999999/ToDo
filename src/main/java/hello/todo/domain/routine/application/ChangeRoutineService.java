package hello.todo.domain.routine.application;

import hello.todo.domain.routine.application.command.ChangeRoutineCommand;
import hello.todo.domain.routine.domain.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChangeRoutineService {

    private final RoutineQueryService routineQueryService;

    public void changeRoutine(Long routineId, Long memberId, ChangeRoutineCommand changeRoutineCommand) {
        //루틴 조회
        Routine routine = routineQueryService.findExistingRoutine(routineId, memberId);

        //Optional과 더티 체킹으로 값 변경
        changeRoutineCommand.days().ifPresent(routine::changeDays);
        changeRoutineCommand.name().ifPresent(routine::changeName);
        changeRoutineCommand.endDate().ifPresent(routine::changeEndDate);

        //TODO 바꾼 루틴 주기가 오늘도 포함되어 있다면 할 일 생성 이벤트 발행
    }
}
