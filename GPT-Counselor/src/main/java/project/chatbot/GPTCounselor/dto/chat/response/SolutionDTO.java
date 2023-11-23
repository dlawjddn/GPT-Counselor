package project.chatbot.GPTCounselor.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
@Getter
public class SolutionDTO {
    private Boolean hasSolution;
    private String solution;

    public SolutionDTO(String solution) {
        this.hasSolution = solution != null;
        this.solution = hasSolution ? solution : "doesn't exist"; // 기본값으로 빈 문자열 사용
    }

    // getter 및 다른 메소드들
}

