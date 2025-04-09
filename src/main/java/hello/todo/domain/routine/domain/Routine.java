package hello.todo.domain.routine.domain;

import hello.todo.common.BaseTimeEntity;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.exception.CycleNotFoundException;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineReqDTO;
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



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;


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

    private void setCycles(List<Cycle> cycles){
        //사이클이 없다면 예외처리
        if(cycles.isEmpty()){
            throw new CycleNotFoundException("주기가 존재하지 않습니다.");
        }

        cycles.forEach(cycle -> addCycle(cycle));
    }


    // 멤버 팩토리 메소드를
    private Routine(String name, List<Cycle> cycles, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        setCycles(cycles);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //TODO: end가 start보다 더 앞선 경우 예외 보내줘야함
    static public Routine of(CreateRoutineReqDTO dto){
        return new Routine(dto.name(),dto.cycles(),dto.startDate(),dto.endDate());
    }
}


