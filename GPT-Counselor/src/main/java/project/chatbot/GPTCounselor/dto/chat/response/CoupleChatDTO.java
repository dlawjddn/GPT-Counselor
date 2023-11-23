package project.chatbot.GPTCounselor.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CoupleChatDTO {
    private String userString;
    private String gptString;
}
