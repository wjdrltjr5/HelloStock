package deu.hellostock.controller;

import deu.hellostock.dto.BoardDTO;
import deu.hellostock.dto.CommentDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.service.BoardService;
import deu.hellostock.service.CommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentsService commentsService;

    @GetMapping("/")
    public String home(Model model,@RequestParam(value = "page",defaultValue = "1")int page){
        PageRequest pageRequest = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "boardid"));
        Page<BoardDTO> paging = boardService.findAll(pageRequest);
        List<BoardDTO> boards = paging.getContent();
        model.addAttribute("boards",boards);
        model.addAttribute("nextPage", paging.hasNext());
        model.addAttribute("previousPage",paging.hasPrevious());
        model.addAttribute("pageNum",page);
        return "boards";
    }

    @GetMapping("/board")
    public String boardWriteForm(Model model){
        model.addAttribute("board",new BoardDTO());
        return "board-write";
    }
    @PostMapping("/board")
    public String boardWrite(@Validated @ModelAttribute("board") BoardDTO boardDto, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.debug("errors = {}",bindingResult);
            return "board-write";
        }
        HttpSession session = request.getSession();
        SessionDTO member = (SessionDTO) session.getAttribute("member");
        boardService.write(boardDto,member);
        return "redirect:/";
    }
    @GetMapping("/board/{board-id}")
    public String boardView(@PathVariable("board-id")Long boardId,Model model,HttpServletRequest request
            , @RequestParam(value = "page",defaultValue = "1")int page){
        PageRequest pageRequest = PageRequest.of(page-1, 5);
        HttpSession session = request.getSession();
        SessionDTO member = (SessionDTO) session.getAttribute("member");
        BoardDTO board = boardService.findBoard(boardId);
        Page<CommentDTO> commentsPaging = commentsService.findAll(pageRequest, boardId);
        List<CommentDTO> comments = commentsPaging.getContent();
        long count = commentsPaging.getTotalElements();
        if (member != null && board.getMemberid().equals(member.getMemberid())){
            model.addAttribute("writer",true);
        }
        model.addAttribute("commentsNextPage",commentsPaging.hasNext());
        model.addAttribute("commentsPrePage",commentsPaging.hasPrevious());
        model.addAttribute("pageNum",page);
        model.addAttribute("comments",comments);
        model.addAttribute("count",count);
        model.addAttribute("board", board);
        return "board";
    }
    @GetMapping("/board/{board-id}/edit")
    public String boardUpdateView(@PathVariable("board-id")Long boardId,Model model){
        model.addAttribute("board",boardService.findBoard(boardId));
        return "board-update";
    }

    @PutMapping("/board/{board-id}/edit")
    public String boardUpdate(@PathVariable("board-id")Long boardId,@ModelAttribute("board") BoardDTO boardDto,
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
    public String boardSearch(@RequestParam String keyword,@RequestParam(value = "page",defaultValue = "1")int page ,Model model){
        PageRequest pageRequest = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC,"boardid"));
        Page<BoardDTO> paging = boardService.search(keyword, keyword, pageRequest);
        List<BoardDTO> boards = paging.getContent();
        model.addAttribute("boards", boards);
        model.addAttribute("nextPage",paging.hasNext());
        model.addAttribute("previousPage",paging.hasPrevious());
        model.addAttribute("pageNum",page);
        model.addAttribute("keyword",keyword);
        return "boards-search";
    }
}
