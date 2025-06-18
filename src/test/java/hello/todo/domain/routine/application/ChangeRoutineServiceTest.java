package hello.todo.domain.routine.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import hello.todo.domain.routine.application.command.ChangeRoutineCommand;
import hello.todo.domain.routine.domain.Day;
import hello.todo.domain.routine.domain.Routine;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChangeRoutineServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ChangeRoutineService changeRoutineService;

    @Test
    void 루틴의_이름_요일_종료일을_변경() {
        //given
        Member member = createMember();
        Routine routine = createRoutine(member.getId());

        Set<Day> days = new HashSet<>();
        days.add(Day.MON);
        days.add(Day.WED);
        days.add(Day.TUE);
        days.add(Day.SAT);
        ChangeRoutineCommand changeRoutineCommand = new ChangeRoutineCommand(Optional.of("이름변경"), Optional.of(days), Optional.of(LocalDate.now()));

        //when
        changeRoutineService.changeRoutine(routine.getId(), member.getId(), changeRoutineCommand);
        em.flush();
        em.clear();

        //then
        Routine changedRoutine = em.find(Routine.class,routine.getId());
        assertEquals("이름변경",changedRoutine.getName(),"루틴 이름은 변경될 수 있다.");
        assertEquals(days,changedRoutine.getDays(),"루틴 주기는 변경될 수 있다.");
        assertEquals(LocalDate.now(),changedRoutine.getEndDate(),"루틴 종료날짜는 변경될 수 있다.");
    }

    private Member createMember() {
        Member member = Member.of("test@email", "testNickname", Role.ROLE_USER, "1");
        em.persist(member);
        return member;
    }

    private Routine createRoutine(Long memberId) {
        Set<Day> days = new HashSet<>();
        days.add(Day.MON);
        days.add(Day.WED);
        days.add(Day.FRI);

        Routine routine = Routine.of(memberId, "알고리즘 문제 풀기", days, LocalDate.now(), LocalDate.now().plusDays(7));
        em.persist(routine);
        return routine;
    }
}