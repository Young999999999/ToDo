package hello.todo.domain.routine.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor

public class RemoveRoutineService {

    private final MemberRepository memberRepository;

    @Transactional
    public void removeRoutine(Long memberId,Long deleteRoutineId){
        Member member = findExistingMember(memberRepository,memberId);

        //member.removeRoutine(deleteRoutineId);
    }
}
