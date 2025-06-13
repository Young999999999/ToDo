package hello.todo.domain.routine.application;

import hello.todo.domain.member.application.MemberQueryService;
import hello.todo.domain.member.domain.MemberRepository;
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
    public Long createRoutine(Long memberId, String routineName, LocalDate startDate, LocalDate endDate, Set<Day> days) {
        //멤버 아이디 확인하기.
        memberQueryService.findExistingMember(memberId);
        Routine routine = Routine.of(memberId, routineName, days, startDate, endDate);
        routineRepository.save(routine);
        return routine.getId();
    }
}
