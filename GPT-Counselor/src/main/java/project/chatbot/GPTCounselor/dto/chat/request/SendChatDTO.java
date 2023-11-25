package project.chatbot.GPTCounselor.dto.chat.request;

import lombok.Getter;

@Getter
public class SendChatDTO {
    private Long consultingId;
    private String userChat;
}
