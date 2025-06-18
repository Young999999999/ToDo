package hello.todo.domain.routine.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.member.application.CreateMemberService;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import hello.todo.domain.routine.application.command.CreateRoutineCommand;
import hello.todo.domain.routine.domain.Day;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.domain.RoutineRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CreateRoutineServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    CreateRoutineService createRoutineService;

    @Autowired
    CreateMemberService createMemberService;

    @Autowired
    RoutineRepository routineRepository;

    @Test
    void 루틴_생성() {
        //given
        Member member = createMember();

        String routineName = "알고리즘 1문제 풀기";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(6);
        Set<Day> days = new HashSet<>();
        days.add(Day.MON);
        days.add(Day.WED);
        days.add(Day.FRI);
        CreateRoutineCommand command = new CreateRoutineCommand(routineName,startDate,endDate,days);

        //when
        Long routineId = createRoutineService.createRoutine(member.getId(), command);

        //then
        Routine routine = routineRepository.findRoutineByIdAndMemberId(routineId, member.getId()).get();
        assertEquals(member.getId(), routine.getMemberId());
        assertEquals("알고리즘 1문제 풀기", routine.getName());
        assertEquals(startDate, routine.getStartDate());
        assertEquals(endDate, routine.getEndDate());
        assertEquals(days, routine.getDays());
    }

    @Test
    void 루틴_생성_날짜오류() {
        //given
        Member member = createMember();

        String routineName = "알고리즘 1문제 풀기";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        Set<Day> days = new HashSet<>();
        days.add(Day.MON);
        days.add(Day.WED);
        days.add(Day.FRI);
        CreateRoutineCommand command = new CreateRoutineCommand(routineName,startDate,endDate,days);

        //when & then
        assertThrows(CustomException.class, () -> {
            createRoutineService.createRoutine(member.getId(), command);
        });
    }

    private Member createMember() {
        Member member = Member.of("test@email", "testNickname", Role.ROLE_USER, "1");
        em.persist(member);
        return member;
    }
}