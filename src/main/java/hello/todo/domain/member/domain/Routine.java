package hello.todo.domain.member.domain;

import hello.todo.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseTimeEntity {

    // 멤버 팩토리 메소드를 통해서만 만들도록 protected
    protected Routine(String name, Cycle cycle, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.cycle = cycle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Cycle cycle;

    public enum Cycle {
        MON,TUE,WED,THU,FRI,SAT,SUN;
    }

    private LocalDate startDate;

    private LocalDate endDate;

    public void changeMember(Member member){
        this.member = member;
    }
}


