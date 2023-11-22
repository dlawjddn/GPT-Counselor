package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Consulting;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> {

}
