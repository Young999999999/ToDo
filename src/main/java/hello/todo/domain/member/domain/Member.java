package hello.todo.domain.member.domain;

import hello.todo.common.BaseTimeEntity;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineReqDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    //TODO 비밀번호 암호화
    private String password;

    private String nickname;


    //팩토리 메서드 패턴
    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    static public Member of(String email, String password, String nickname) {
        return new Member(email, password, nickname);
    }



}
