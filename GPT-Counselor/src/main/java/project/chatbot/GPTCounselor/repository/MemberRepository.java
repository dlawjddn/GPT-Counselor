package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
}
