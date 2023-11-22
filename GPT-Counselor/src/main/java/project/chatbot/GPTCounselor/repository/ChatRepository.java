package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
