package hello.todo.domain.member.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;

public final class MemberServiceHelper {

    public static Member findExistingMember(MemberRepository memberRepository, Long memberId) {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

}


