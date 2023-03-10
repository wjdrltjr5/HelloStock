package deu.hellostock.controller;

import deu.hellostock.dto.MemberDto;
import deu.hellostock.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "/signin";
    }
    @GetMapping("/signup")
    public String signup(){
        return "/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Validated MemberDto memberDto, BindingResult bindingResult, Model model){
        log.info("memberDto={}",memberDto.toString());
//        if(bindingResult.hasErrors()){
//            return"/signup";
//        }
        memberService.signUp(memberDto);
        return "redirect:/signin";
    }

}
