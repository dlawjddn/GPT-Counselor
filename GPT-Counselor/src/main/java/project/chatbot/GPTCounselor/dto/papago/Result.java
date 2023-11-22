package project.chatbot.GPTCounselor.dto.papago;

import lombok.Getter;

@Getter
public class Result {
    /**
     * "srcLangType": "ko",
     *             "tarLangType": "en",
     *             "translatedText": "I'm Lim Jungwoo.",
     *             "engineType": "N2MT"
     */
    private String srcLangType;
    private String tarLangType;
    private String translatedText;
    private String engineType;

}
