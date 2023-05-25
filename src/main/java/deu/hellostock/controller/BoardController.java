package deu.hellostock.controller;

import deu.hellostock.dto.BoardDTO;
import deu.hellostock.dto.CommentDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.UploadFile;
import deu.hellostock.service.BoardService;
import deu.hellostock.service.CommentsService;
import deu.hellostock.service.LikeService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final LikeService likeService;
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
        log.info("content = {}",boardDto.getContent());
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
        int like = likeService.getLikeCount(boardId);
        if (member != null && board.getMemberid().equals(member.getMemberid())){
            model.addAttribute("writer",true);
        }
        if(member != null){
            model.addAttribute("memberId",member.getMemberid());
        }
        model.addAttribute("commentsNextPage",commentsPaging.hasNext());
        model.addAttribute("commentsPrePage",commentsPaging.hasPrevious());
        model.addAttribute("pageNum",page);
        model.addAttribute("comments",comments);
        model.addAttribute("count",count);
        model.addAttribute("board", board);
        model.addAttribute("like",like);
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
    @PostMapping("/board/upload-image")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile imageFile, Model model) throws IOException {
        log.info("imageFile = {}",imageFile);
        UploadFile uploadFile = boardService.storeFile(imageFile);
        log.info(boardService.getFullPath(uploadFile.getUploadFileName()));
        return "/images/" +uploadFile.getStoreFileName();
    }

    @GetMapping("/board/{boardId}/like")
    public String clickLike(@PathVariable Long boardId, HttpSession session,RedirectAttributes redirectAttributes){
        SessionDTO member = (SessionDTO) session.getAttribute("member");
        String msg = likeService.clickLike(boardId, member.getMemberid());
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/board/"+boardId;
    }

}
