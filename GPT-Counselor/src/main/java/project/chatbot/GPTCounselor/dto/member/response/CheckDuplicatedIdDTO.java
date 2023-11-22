package project.chatbot.GPTCounselor.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckDuplicatedIdDTO {
    private boolean notDuplicated;
}
