package project.chatbot.GPTCounselor.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;
    @Column(columnDefinition = "LONGTEXT")
    private String userMessage;
    @Column(columnDefinition = "LONGTEXT")
    private String gptMessage;
    private LocalDateTime createdAt;
    @Builder
    public Chat(Consulting consulting, String userMessage, String gptMessage) {
        this.consulting = consulting;
        this.userMessage = userMessage;
        this.gptMessage = gptMessage;
        this.createdAt = LocalDateTime.now();
    }
}
