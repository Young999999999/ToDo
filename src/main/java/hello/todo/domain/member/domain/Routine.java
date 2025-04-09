package hello.todo.domain.member.domain;

import hello.todo.common.BaseTimeEntity;
import hello.todo.domain.member.domain.exception.CycleNotFoundException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Routine extends BaseTimeEntity {

    // 멤버 팩토리 메소드를 통해서만 만들도록 protected
    protected Routine(String name, List<Cycle> cycles, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        setCycles(cycles);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "routine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Cycle> cycles = new ArrayList<>();

    //양방향 원자화 메서드
    public void addCycle(Cycle cycle){
        cycles.add(cycle);
        cycle.changeRoutine(this);
    }

    public void removeCycle(Long removeCycleId){
        cycles.removeIf(cycle -> (cycle.getId().equals(removeCycleId)));
    }

    public void changeMember(Member member){
        this.member = member;
    }

    private void setCycles(List<Cycle> cycles){
        //사이클이 없다면 예외처리
        if(cycles.isEmpty()){
            throw new CycleNotFoundException("주기가 존재하지 않습니다.");
        }

        this.cycles = cycles;
        cycles.forEach(cycle -> cycle.changeRoutine(this));
    }
}


