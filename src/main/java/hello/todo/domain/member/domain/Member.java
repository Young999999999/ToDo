package hello.todo.domain.member.domain;

import hello.todo.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    //팩토리 메서드 패턴
    private Member(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    static public Member of(String email, String nickname) {
        return new Member(email, nickname);
    }

    @Getter
    enum Role {
        USER,ADMIN;
    }
}
