package project.chatbot.GPTCounselor.dto.gpt.request;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Message {
    private String role;
    private String content;
    @Builder
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
