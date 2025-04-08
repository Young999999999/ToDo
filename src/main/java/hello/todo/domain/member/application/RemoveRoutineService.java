package hello.todo.domain.member.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.domain.Routine;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static hello.todo.domain.member.application.MemberServiceHelper.*;

@Service
@RequiredArgsConstructor

public class RemoveRoutineService {

    private final MemberRepository memberRepository;

    @Transactional
    public void removeRoutine(Long memberId,Long deleteRoutineId){
        Member member = findExistingMember(memberRepository,memberId);
        //조건에 따라 컬렉션에서 삭제
        member.getRoutines().removeIf(routine -> routine.getId().equals(deleteRoutineId));
    }
}
