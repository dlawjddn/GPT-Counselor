package project.chatbot.GPTCounselor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatbot.GPTCounselor.domain.Consulting;
import project.chatbot.GPTCounselor.domain.Member;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveConsultingDTO;
import project.chatbot.GPTCounselor.repository.ConsultingRepository;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;
    private final MemberService memberService;
    @Transactional
    public void saveConsulting(SaveConsultingDTO saveConsultingDTO){
        consultingRepository.save(new Consulting(saveConsultingDTO.getTitle(), getMember()));
    }
    private Member getMember(){
        return memberService.findByUsername("dlawjddn");
    }
}
