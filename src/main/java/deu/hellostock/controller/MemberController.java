package deu.hellostock.controller;

import deu.hellostock.dto.MemberDTO;
import deu.hellostock.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signin")
    public String signin(){
        return "signin";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Validated MemberDTO memberDto, BindingResult bindingResult, Model model){
        log.info("memberDto={}",memberDto.toString());
//        다른거 개발 끝나고 풀기
//        if(bindingResult.hasErrors()){
//            return"/signup";
//        }
        memberService.signUp(memberDto);
        return "redirect:/signin";
    }

}
