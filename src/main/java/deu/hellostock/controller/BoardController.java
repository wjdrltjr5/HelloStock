package deu.hellostock.controller;

import deu.hellostock.dto.BoardDto;
import deu.hellostock.dto.SessionDto;
import deu.hellostock.repository.BoardRepository;
import deu.hellostock.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public String boardWriteForm(){
        return "/boardwrite";
    }
    @PostMapping("/board")
    public String boardWrite(@Validated BoardDto boardDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionDto member = (SessionDto) session.getAttribute("member");
        boardService.write(boardDto,member);
        return "redirect:/";
    }
}
