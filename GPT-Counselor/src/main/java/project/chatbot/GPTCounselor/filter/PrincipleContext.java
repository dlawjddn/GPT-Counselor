package project.chatbot.GPTCounselor.filter;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import project.chatbot.GPTCounselor.domain.Member;

import java.util.Collection;
@Getter
public class PrincipleContext extends User {
    private Member member;
    public PrincipleContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.member = member;
    }
}
