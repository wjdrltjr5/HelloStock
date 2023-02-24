package deu.hellostock.controller;

import deu.hellostock.dto.BoardDto;
import deu.hellostock.dto.SessionDto;
import deu.hellostock.entity.Member;
import deu.hellostock.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model){
        List<BoardDto> boards = boardService.findAll();
        model.addAttribute("boards",boards);
        return "boards";
    }

    @GetMapping("/board")
    public String boardWriteForm(Model model){
        model.addAttribute("board",new BoardDto());
        return "board-write";
    }
    @PostMapping("/board")
    public String boardWrite(@Validated @ModelAttribute("board")BoardDto boardDto, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.debug("errors = {}",bindingResult);
            return "/board-write";
        }
        HttpSession session = request.getSession();
        SessionDto member = (SessionDto) session.getAttribute("member");
        boardService.write(boardDto,member);
        return "redirect:/";
    }
    @GetMapping("/board/{board-id}")
    public String boardView(@PathVariable("board-id")Long boardId,Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionDto member = (SessionDto) session.getAttribute("member");
        BoardDto board = boardService.findBoard(boardId);
        if (member != null && board.getMember().getId().equals(member.getMemberid())){
            model.addAttribute("writer",true);
        }
        model.addAttribute("board", board);
        return "/board";
    }
    @GetMapping("/board/{board-id}/edit")
    public String boardUpdateView(@PathVariable("board-id")Long boardId,Model model){
        model.addAttribute("board",boardService.findBoard(boardId));
        return "/board-update";
    }

    @PutMapping("/board/{board-id}/edit")
    public String boardUpdate(@PathVariable("board-id")Long boardId,@ModelAttribute("board")BoardDto boardDto,
                              Model model){
        boardService.update(boardId,boardDto);
        model.addAttribute("board",boardDto);
        return "redirect:/board/"+boardId;
    }
    @DeleteMapping("/board/{board-id}")
    public String boardDelete(@PathVariable("board-id")Long boardId){
        boardService.delete(boardId);
        return "redirect:/";
    }
    @GetMapping("/boards/search")
    public String boardSearch(@RequestParam("q")String keyword, Model model){
        model.addAttribute("boards",boardService.search(keyword,keyword));
        return "boards";
    }
}
