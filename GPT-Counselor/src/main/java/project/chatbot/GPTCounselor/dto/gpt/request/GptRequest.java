package project.chatbot.GPTCounselor.dto.gpt.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GptRequest {
    private final String model = "gpt-3.5-turbo";
    private List<Message> messages;
    @Builder
    public GptRequest(List<Message> messages) {
        this.messages = messages;
    }
}
