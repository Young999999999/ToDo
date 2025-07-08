package hello.todo.domain.task.domain;

import hello.todo.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    private Long memberId;

    private Long routineId;

    private String title;

    private LocalDate date;

    private boolean isDone = false;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    public enum Type {
        NORMAL, ROUTINE
    }

    //팩토리 메서드.
    public static Task of(Long memberId, Long routineId, String title, LocalDate date,Type type){
        return new Task(memberId, routineId, title, date, type);
    }

    public static Task of(Long memberId, String title, LocalDate date,Type type){
        return new Task(memberId, title, date, type);
    }

    public Task (Long memberId, Long routineId, String title, LocalDate date,Type type){
        this.memberId = memberId;
        this.routineId = routineId;
        this.title = title;
        this.date = date;
        this.type = type;
    }

    public Task (Long memberId, String title, LocalDate date,Type type){
        this.memberId = memberId;
        this.title = title;
        this.date = date;
        this.type = type;
    }

}


