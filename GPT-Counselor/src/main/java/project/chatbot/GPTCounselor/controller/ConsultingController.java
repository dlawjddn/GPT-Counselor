package project.chatbot.GPTCounselor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.chatbot.GPTCounselor.dto.chat.response.ConsultingChatDTO;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveConsultingDTO;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveFeedbackDTO;
import project.chatbot.GPTCounselor.dto.consulting.response.SaveSolutionDTO;
import project.chatbot.GPTCounselor.dto.consulting.response.ShowConsultingSimpleDTO;
import project.chatbot.GPTCounselor.service.ConsultingService;
import project.chatbot.GPTCounselor.service.FeedBackService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ConsultingController {
    private final ConsultingService consultingService;
    private final FeedBackService feedBackService;
    @PostMapping("/users/consulting")
    public void saveConsulting(@RequestBody SaveConsultingDTO saveConsultingDTO){
        consultingService.saveConsulting(saveConsultingDTO);
    }
    @GetMapping("/users/consulting")
    public List<ShowConsultingSimpleDTO> showSimpleConsults(){
        return consultingService.showSimpleConsults();
    }
    @GetMapping("/users/consulting/")
    public ConsultingChatDTO showChatsByConsulting(@RequestParam Long id){
        return consultingService.showChatsByConsulting(id);
    }
    @GetMapping("/users/consulting/solution")
    public SaveSolutionDTO makeSolution(@RequestParam Long id) throws JsonProcessingException {
        return consultingService.makeSolution(id);
    }
    @PostMapping("/users/feedback")
    public void saveFeedback(@RequestBody SaveFeedbackDTO saveFeedbackDTO){
        feedBackService.saveFeedback(saveFeedbackDTO);
    }
}
