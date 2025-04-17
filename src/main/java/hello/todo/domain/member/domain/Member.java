package hello.todo.domain.member.domain;

import hello.todo.domain.common.BaseTimeEntity;
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

    private Long sub;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    //팩토리 메서드 패턴
    private Member(String email, String nickname,Role role,Long sub) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.sub = sub;
    }

    static public Member of(String email, String nickname,Role role,Long sub) {
        return new Member(email, nickname,role,sub);
    }


}
