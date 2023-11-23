package project.chatbot.GPTCounselor.dto.consulting.request;

import lombok.Getter;

@Getter
public class SaveFeedbackDTO {
    private Long consultingId;
    private int length;
    private int quality;
}
