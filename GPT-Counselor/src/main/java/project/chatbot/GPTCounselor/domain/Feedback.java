package project.chatbot.GPTCounselor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Feedback {
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Consulting consulting;
    private int length;
    private double satisfied;

    public Feedback(Consulting consulting, int length, double satisfied) {
        this.consulting = consulting;
        this.length = length;
        this.satisfied = satisfied;
    }
}

