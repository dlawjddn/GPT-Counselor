package project.chatbot.GPTCounselor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatbot.GPTCounselor.domain.Chat;
import project.chatbot.GPTCounselor.domain.Consulting;
import project.chatbot.GPTCounselor.domain.Member;
import project.chatbot.GPTCounselor.dto.chat.request.SendChatDTO;
import project.chatbot.GPTCounselor.dto.chat.response.ConsultingChatDTO;
import project.chatbot.GPTCounselor.dto.chat.response.CoupleChatDTO;
import project.chatbot.GPTCounselor.dto.chat.response.SolutionDTO;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveConsultingDTO;
import project.chatbot.GPTCounselor.dto.consulting.response.SaveSolutionDTO;
import project.chatbot.GPTCounselor.dto.consulting.response.ShowConsultingSimpleDTO;
import project.chatbot.GPTCounselor.repository.ConsultingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;
    private final MemberService memberService;
    private final ChatService chatService;
    @Transactional
    public void saveConsulting(SaveConsultingDTO saveConsultingDTO){
        if (existedByTitle(saveConsultingDTO.getTitle()))
            throw new IllegalArgumentException("already existed consulting title");
        Consulting consulting = consultingRepository.save(new Consulting(saveConsultingDTO.getTitle(), getMember()));
        chatService.saveChat(consulting, "system", "You are a helpful counselor");
    }
    public boolean existedByTitle(String title){
        return consultingRepository.existsByTitle(title);
    }
    public Consulting findById(Long consultingId) {
        return consultingRepository.findById(consultingId)
                .orElseThrow(() -> new RuntimeException("doesn't exist consulting"));
    }
    public List<ShowConsultingSimpleDTO> showSimpleConsults() {
        return consultingRepository.findAllByMember(getMember()).stream()
                .map(consulting -> new ShowConsultingSimpleDTO(consulting.getId(), consulting.getTitle()))
                .toList();
    }
    public ConsultingChatDTO showChatsByConsulting(Long id) {
        Consulting consulting = findById(id);
        List<CoupleChatDTO> chats = new ArrayList<>();
        for (int i=1; i<consulting.getChats().size(); i++){
            if (i %2 == 0){ // 짝수인경우 -> gptchat
                chats.add(new CoupleChatDTO(consulting.getChats().get(i-1).getContent(), consulting.getChats().get(i).getContent()));
            }
        }
        return new ConsultingChatDTO(chats, consulting.getSolution(), (consulting.getFeedBack() != null));
    }
    private Member getMember(){
        return memberService.findByUsername("dlawjddn");
    }
    public SaveSolutionDTO makeSolution(Long id) throws JsonProcessingException {
        Consulting consulting = findById(id);
        return chatService.makeSolution(consulting);
    }
}
