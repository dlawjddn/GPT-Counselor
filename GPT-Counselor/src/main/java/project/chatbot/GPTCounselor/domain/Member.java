package project.chatbot.GPTCounselor.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "member")
    private List<Consulting> consultings = new ArrayList<>();
    private String role;
    @Builder
    public Member(String username, String password){
        this.username = username;
        this.password = password;
        this.role = "USER";
    }
}
