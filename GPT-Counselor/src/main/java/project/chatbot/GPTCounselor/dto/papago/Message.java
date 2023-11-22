package project.chatbot.GPTCounselor.dto.papago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Message {
    @JsonProperty("@type")
    private String type;
    @JsonProperty("@service")
    private String service;
    @JsonProperty("@version")
    private String version;
    private Result result;
}
