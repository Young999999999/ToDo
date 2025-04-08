package hello.todo.domain.member.application;

import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.domain.exception.MemberNotFoundException;

public final class MemberServiceHelper {

        public static Member findExistingMember(MemberRepository memberRepository, Long memberId){
            Member member = memberRepository.findMemberById(memberId)
                    .orElseThrow(() -> new MemberNotFoundException("Member not found"));

            return member;
        }

}


