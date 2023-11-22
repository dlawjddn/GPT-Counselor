package project.chatbot.GPTCounselor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.chatbot.GPTCounselor.dto.consulting.request.SaveConsultingDTO;
import project.chatbot.GPTCounselor.service.ConsultingService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ConsultingController {
    private final ConsultingService consultingService;
    @PostMapping("/users/consulting")
    public void saveConsulting(@RequestBody SaveConsultingDTO saveConsultingDTO){
        consultingService.saveConsulting(saveConsultingDTO);
    }
}
