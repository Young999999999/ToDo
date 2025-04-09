package hello.todo.domain.member.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.domain.Routine;
import hello.todo.domain.member.presentation.dto.request.CreateRoutineReqDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CreateRoutineService {

    private final MemberRepository memberRepository;

    @Transactional
    public void addRoutine(CreateRoutineReqDTO dto,Long memberId){
        Member member = findExistingMember(memberRepository,memberId);
        Routine routine = member.createRoutine(dto);
        System.out.println(routine);
        member.addRoutine(routine);
    }
}
