package hello.todo.domain.member.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.presentation.dto.request.CreateMemberReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.findExistingMember;

@Service
@RequiredArgsConstructor
public class CreateMemberService {

    private final MemberRepository memberRepository;

    public void createMember(CreateMemberReqDTO dto){
        Member member = Member.of(dto.email(),dto.password(),dto.nickname());

        memberRepository.save(member);
    }


}
