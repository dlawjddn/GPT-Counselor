package project.chatbot.GPTCounselor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatbot.GPTCounselor.domain.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
