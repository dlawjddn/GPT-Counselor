package project.chatbot.GPTCounselor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.chatbot.GPTCounselor.dto.member.request.SaveMemberDTO;
import project.chatbot.GPTCounselor.dto.member.response.CheckDuplicatedIdDTO;
import project.chatbot.GPTCounselor.service.MemberService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/auth/register")
    public void registerMember(@RequestBody SaveMemberDTO saveMemberDTO){
        memberService.saveMember(saveMemberDTO);
    }
    @GetMapping("/auth/isDuplicated")
    public CheckDuplicatedIdDTO checkDuplicatedId(@RequestParam String id){
        return memberService.isDuplicated(id);
    }

}
