package project.chatbot.GPTCounselor.dto.gpt.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GptResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
