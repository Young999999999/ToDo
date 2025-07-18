package hello.todo.domain.routine.application;

import hello.todo.domain.member.application.MemberQueryService;
import hello.todo.domain.routine.application.command.CreateRoutineCommand;
import hello.todo.domain.routine.domain.Day;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateRoutineService {

    private final RoutineRepository routineRepository;
    private final MemberQueryService memberQueryService;

    @Transactional
    public Long createRoutine(Long memberId, CreateRoutineCommand command) {
        //존재하는 멤버인지 확인하기.
        memberQueryService.findExistingMember(memberId);
        Routine routine = Routine.of(memberId, command.routineName(), command.days(), command.startDate(), command.endDate());
        routineRepository.save(routine);
        //TODO refator: startDate가 오늘이라면 routineId를 이용한 일정 생성 이벤트를 발행한다.
        return routine.getId();
    }
}
