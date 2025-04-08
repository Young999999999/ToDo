package hello.todo.domain.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository{

    Optional<Member> findMemberById(Long memberId);

    Member save(Member member);
}
