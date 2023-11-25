package project.chatbot.GPTCounselor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatbot.GPTCounselor.domain.Consulting;
import project.chatbot.GPTCounselor.domain.Feedback;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveFeedbackDTO;
import project.chatbot.GPTCounselor.repository.FeedbackRepository;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final FeedbackRepository feedbackRepository;
    private final ConsultingService consultingService;
    @Transactional
    public void saveFeedback(SaveFeedbackDTO saveFeedbackDTO) {
        Consulting consulting = consultingService.findById(saveFeedbackDTO.getConsultingId());
        feedbackRepository.save(new Feedback(consulting, saveFeedbackDTO.getLength(), saveFeedbackDTO.getQuality()));
    }
}
