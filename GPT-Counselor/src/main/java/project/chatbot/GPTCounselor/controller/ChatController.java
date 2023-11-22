package project.chatbot.GPTCounselor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.chatbot.GPTCounselor.dto.gpt.response.GptResponse;
import project.chatbot.GPTCounselor.service.ChatService;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ChatService chatService;
    @PostMapping("/api/gpt")
    public GptResponse sendMessage() throws JsonProcessingException {
        return chatService.sendMessage();
    }
}
