package hello.todo.domain.member.application;

import hello.todo.domain.common.exception.CustomException;
import hello.todo.domain.common.exception.ErrorCode;
import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import hello.todo.domain.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findExistingMember(Long memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    //TODO: google sub 기반 조회 구현.
    public Optional<Member> findMemberBySub(String sub) {
        return Optional.empty();
    }

    //TODO: 재발급을 위한 유저 아이디 기반 권한 조회 구현
    public Role findRoleByMemberId(Long memberId) {
        return Role.ROLE_USER;
    }

}
