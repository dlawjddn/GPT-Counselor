package project.chatbot.GPTCounselor.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Consulting {
    @Id
    @Column(name = "consulting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "consulting")
    private List<Chat> chats;
    private String solution;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Feedback feedBack;
    @Builder
    public Consulting(String title, Member member) {
        this.title = title;
        this.member = member;
    }
}
