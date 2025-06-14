package hello.todo.domain.routine.domain;

import hello.todo.domain.common.BaseTimeEntity;
import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Routine extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "day", joinColumns = @JoinColumn(name = "routine_id"))
    @Enumerated(value = EnumType.STRING)
    Set<Day> days = new HashSet<>();

    //루틴 팩토리 메소드를 위한 생성자
    private Routine(Long memberId, String name, Set<Day> days, LocalDate startDate, LocalDate endDate) {
        this.memberId = memberId;
        this.name = name;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    static public Routine of(Long memberId, String name, Set<Day> days, LocalDate startDate, LocalDate endDate) {
        validateDate(startDate, endDate);
        return new Routine(memberId, name, days, startDate, endDate);
    }

    /**
     * 루틴 시작일과 종료일은 최소 일주일의 차이가 나야한다.
     * 루틴 시작일이 오늘인지 검증한다.
     */
    private static void validateDate(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (!now.equals(startDate) || endDate.isBefore(startDate.plusDays(6))) {
            throw new CustomException(ErrorCode.ROUTINE_DATE_INVALID);
        }
    }
}


