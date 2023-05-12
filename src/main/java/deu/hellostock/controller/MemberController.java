package deu.hellostock.controller;

import deu.hellostock.dto.MemberDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        if (memberService.usernameDuplicationCheck(memberDto.getUsername())){
            return "redirect:/signup";
        }
        memberService.signUp(memberDto);
        return "redirect:/signin";
    }
    @GetMapping("/mypage")
    public String mypage(Model model, @SessionAttribute("member")SessionDTO member){
        model.addAttribute("check",true);
        model.addAttribute("nickname",memberService.getNickname(member.getUsername()));
        return "mypage";
    }
    @GetMapping("/mypage/nickname")
    public String nickname(Model model,@SessionAttribute("member")SessionDTO member){
        model.addAttribute("check",false);
        model.addAttribute("nickname",memberService.getNickname(member.getUsername()));
        return "mypage";
    }
    @PostMapping("/mypage/nickname")
    public String updateNickname(@RequestParam String nickname, @SessionAttribute("member")SessionDTO member){
        if (memberService.updateNickname(nickname,member.getUsername())){
            return "redirect:/mypage";
        }
        //에러메시지 추가하기 추후에
        return "redirect:/mypage/nickname";
    }
}
