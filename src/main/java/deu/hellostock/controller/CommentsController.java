package deu.hellostock.controller;

import deu.hellostock.dto.CommentDTO;
import deu.hellostock.service.CommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;
    @PostMapping("/board/{boardId}/comment")
    public String writeComment(@Validated @ModelAttribute CommentDTO commentForm, BindingResult result, HttpServletRequest request, @PathVariable Long boardId){
        HttpSession session = request.getSession();
        if(result.hasErrors()){
            return "redirect:/board/"+String.valueOf(boardId);
        }
        commentsService.writeComment(session, commentForm.getCommentContent(), boardId);
        log.info("content = {}, boardId = {}",commentForm.getCommentContent(),boardId);
        return "redirect:/board/"+String.valueOf(boardId);
    }
    @DeleteMapping("/board/{boardId}/comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, @PathVariable String boardId){
        commentsService.deleteComment(commentId);
        return "redirect:/board/"+boardId;
    }

}
