package project.chatbot.GPTCounselor.dto.gpt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Choice {
    private Long index;
    private Message message;
    @JsonProperty(value = "finish_reason")
    private String finishReason;
}
