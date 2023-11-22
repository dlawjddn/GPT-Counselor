package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Chat;
import project.chatbot.GPTCounselor.domain.Consulting;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByConsulting(Consulting consulting);
}
