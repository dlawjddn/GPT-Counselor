package project.chatbot.GPTCounselor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatbot.GPTCounselor.domain.Member;
import project.chatbot.GPTCounselor.dto.member.request.SaveMemberDTO;
import project.chatbot.GPTCounselor.dto.member.response.CheckDuplicatedIdDTO;
import project.chatbot.GPTCounselor.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public void saveMember(SaveMemberDTO saveMemberDTO){
        memberRepository.save(
                Member.builder()
                        .username(saveMemberDTO.getId())
                        .password(saveMemberDTO.getPassword())
                        .build());
    }
    public Member findByUsername(String username){
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("doesn't exist member"));
    }

    public CheckDuplicatedIdDTO isDuplicated(String id) {
        return new CheckDuplicatedIdDTO(!memberRepository.existsByUsername(id));
    }
}
