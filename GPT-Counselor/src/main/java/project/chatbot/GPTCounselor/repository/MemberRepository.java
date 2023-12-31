package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);
}
