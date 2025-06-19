package hello.todo.domain.routine.domain;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoutineRepositoryTest {

    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    EntityManager em;

    @DisplayName("사용자의 모든 루틴의 정보를 가져올 수 있다.")
    @Test
    void 모든_루틴_조회() {
        //given
        Member member = createMember("test@Email", "testName", Role.ROLE_USER, "1");
        Set<Day> days = createDays(Day.MON, Day.FRI, Day.WED);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(30L);
        Routine routine1 = createRoutine(member.getId(), "testRoutine1", days, startDate, endDate);
        Routine routine2 = createRoutine(member.getId(), "testRoutine2", days, startDate, endDate);
        Routine routine3 = createRoutine(member.getId(), "testRoutine3", days, startDate, endDate);

        //when
        List<Routine> routines = routineRepository.findAllByMemberId(member.getId());

        //then
        assertThat(routines)
                .hasSize(3)
                .extracting("id", "memberId", "name", "days", "startDate", "endDate")
                .containsExactlyInAnyOrder(
                        tuple(routine1.getId(), member.getId(), "testRoutine1", days, startDate, endDate),
                        tuple(routine2.getId(), member.getId(), "testRoutine2", days, startDate, endDate),
                        tuple(routine3.getId(), member.getId(), "testRoutine3", days, startDate, endDate)
                );
    }

    @DisplayName("사용자의 단건 루틴 정보를 가져올 수 있다.")
    @Test
    void 단건_루틴_조회() {
        //given
        Member member = createMember("test@Email", "testName", Role.ROLE_USER, "1");
        Set<Day> days = createDays(Day.MON, Day.FRI, Day.WED);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(30L);
        Routine givenRoutine = createRoutine(member.getId(), "testRoutine1", days, startDate, endDate);

        //when
        Optional<Routine> routine = routineRepository.findRoutineByIdAndMemberId(givenRoutine.getId(), member.getId());

        //then
        assertThat(routine).isPresent();

        assertThat(routine.get())
                .extracting("id", "memberId", "name", "days", "startDate", "endDate")
                .contains(givenRoutine.getId(), member.getId(), "testRoutine1", days, startDate, endDate);
    }

    private Member createMember(String email, String nickname, Role role, String sub) {
        Member member = Member.of(email, nickname, role, sub);
        em.persist(member);
        em.flush();
        em.clear();
        return member;
    }

    private Routine createRoutine(Long memberId, String name, Set<Day> days, LocalDate startDate, LocalDate endDate) {
        Routine routine = Routine.of(memberId, name, days, startDate, endDate);
        em.persist(routine);
        em.flush();
        em.clear();
        return routine;
    }

    private Set<Day> createDays(Day... days) {
        return new HashSet<>(Arrays.asList(days));
    }
}