package project.chatbot.GPTCounselor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.chatbot.GPTCounselor.dto.chat.request.SendChatDTO;
import project.chatbot.GPTCounselor.dto.chat.response.GptChatDTO;
import project.chatbot.GPTCounselor.service.ChatService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/consulting/chat")
    public GptChatDTO sendMessage(@RequestBody SendChatDTO sendChatDTO) throws JsonProcessingException {
        return chatService.sendMessage(sendChatDTO);
    }
}
