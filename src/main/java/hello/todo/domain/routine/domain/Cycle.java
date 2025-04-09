package hello.todo.domain.routine.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cycle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @Enumerated(value = EnumType.STRING)
    Days days;

    public void changeRoutine(Routine routine){
        this.routine = routine;
    }

    public enum  Days {
        MON,TUE,WED,THU,FRI,SAT,SUN;
    }

}
