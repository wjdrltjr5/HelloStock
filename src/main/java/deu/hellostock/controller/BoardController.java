package deu.hellostock.controller;

import deu.hellostock.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping("/board-write")
    public String boardWriteForm(){
        return "/boardwrite";
    }

}
