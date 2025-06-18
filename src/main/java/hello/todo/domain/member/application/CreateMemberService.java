package hello.todo.domain.member.application;

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
    public Long createMember(String sub) {
        Member member = Member.of("email","nickName",Role.ROLE_USER, sub);
        memberRepository.save(member);
        return member.getId();
    }


}
