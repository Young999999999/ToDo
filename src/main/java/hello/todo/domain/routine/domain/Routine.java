package hello.todo.domain.routine.domain;

import hello.todo.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

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
    @CollectionTable(name = "day",joinColumns = @JoinColumn(name = "routine_id"))
    @Enumerated(value = EnumType.STRING)
    Set<Day> days = new HashSet<>();

    // 멤버 팩토리 메소드를
    private Routine(Long memberId,String name, Set<Day> days, LocalDate startDate, LocalDate endDate) {
        this.memberId = memberId;
        this.name = name;
        this.days = days;
        //setCycles(cycles);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //TODO: end가 start보다 더 앞선 경우 예외 보내줘야함
    static public Routine of(Long memberId,String name, Set<Day> days, LocalDate startDate, LocalDate endDate){
        return new Routine(memberId,name,days,startDate,endDate);
    }
}


