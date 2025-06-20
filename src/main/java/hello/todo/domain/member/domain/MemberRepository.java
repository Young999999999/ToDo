package hello.todo.domain.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findMemberById(Long memberId);
    Optional<Member> findBySub(String sub);
    //TODO : 단일 필드만 얻고 싶으면 JPQL로 바꿔야함. Member를 select하기 때문에 에러가 남.
    Role getRoleById(Long memberId);
}
