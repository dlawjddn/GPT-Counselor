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
    private String role;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private LocalDateTime createdAt;
    @Builder
    public Chat(Consulting consulting, String role, String content) {
        this.consulting = consulting;
        this.role = role;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
