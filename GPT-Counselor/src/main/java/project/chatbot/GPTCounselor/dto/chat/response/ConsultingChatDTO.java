package project.chatbot.GPTCounselor.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ConsultingChatDTO {
    private List<CoupleChatDTO> chats;
    private String solution;
    private boolean feedback;
}
