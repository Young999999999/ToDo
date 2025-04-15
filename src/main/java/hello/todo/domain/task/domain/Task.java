package hello.todo.domain.task.domain;

import hello.todo.common.BaseTimeEntity;
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

    private String name;

    private String title;

    private String content;

    private LocalDate taskDate;

    private boolean isCompleted;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    public enum Type {
        NORMAL, ROUTINE
    }


}


