package hello.todo.domain.member.application;

import hello.todo.domain.member.application.command.CreateMemberCommand;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.domain.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(CreateMemberCommand command) {
        Member member = Member.of(command.email(), command.name(), Role.ROLE_USER, command.sub());
        memberRepository.save(member);
        return member;
    }


}
