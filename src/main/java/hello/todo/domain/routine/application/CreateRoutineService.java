package hello.todo.domain.routine.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.routine.domain.Routine;
import hello.todo.domain.routine.presentation.dto.request.CreateRoutineReqDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CreateRoutineService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createRoutine(CreateRoutineReqDTO dto, Long memberId){
        Member member = findExistingMember(memberRepository,memberId);
        //Routine routine = member.createRoutine(dto);


        //member.addRoutine(routine);
    }
}
