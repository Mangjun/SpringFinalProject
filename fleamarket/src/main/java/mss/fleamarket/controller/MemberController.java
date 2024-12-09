package mss.fleamarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mss.fleamarket.domain.Member;
import mss.fleamarket.dto.member.MemberForm;
import mss.fleamarket.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@ModelAttribute MemberForm memberForm) {
        Member member = new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword());

        memberService.joinMember(member);
        log.info("Member joined : {}", member.getEmail());
        return "redirect:/login";
    }
}
