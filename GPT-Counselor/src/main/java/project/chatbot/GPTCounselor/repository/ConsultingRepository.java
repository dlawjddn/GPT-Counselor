package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Consulting;
import project.chatbot.GPTCounselor.domain.Member;

import java.util.List;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> {
    boolean existsByTitle(String title);
    List<Consulting> findAllByMember(Member member);
}
