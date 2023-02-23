package deu.hellostock.controller;

import deu.hellostock.dto.MemberDto;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import deu.hellostock.repository.MemberRepository;
import deu.hellostock.service.MemberService;
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
public class HomeController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "board";
    }
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
        return "redirect:/";
    }
    @GetMapping("/stock")
    public String stock(){
        return "/stock";
    }
}
