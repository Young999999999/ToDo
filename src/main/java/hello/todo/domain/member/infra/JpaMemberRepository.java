package hello.todo.domain.member.infra;


import hello.todo.domain.member.domain.Member;
import hello.todo.domain.member.domain.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("memberRepository")
public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{
}
