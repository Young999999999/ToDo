package hello.todo.domain.member.domain;

import hello.todo.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_sub",
                columnNames = {"sub"}
        )})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String name;

    private String sub;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    //팩토리 메서드 패턴
    private Member(String email, String name, Role role, String sub) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.sub = sub;
    }

    static public Member of(String email, String name, Role role, String sub) {
        return new Member(email, name, role, sub);
    }

}
