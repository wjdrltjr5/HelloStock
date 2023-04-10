package deu.hellostock.controller;

import deu.hellostock.dto.CommentForm;
import deu.hellostock.repository.CommentsRepository;
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
    public String writeComment(@Validated @ModelAttribute CommentForm commentForm, BindingResult result, HttpServletRequest request, @PathVariable Long boardId){
        HttpSession session = request.getSession();
        if(result.hasErrors()){
            return "redirect:/board/"+String.valueOf(boardId);
        }
        commentsService.writeComment(session, commentForm.getCommentContent(), boardId);
        log.info("content = {}, boardId = {}",commentForm.getCommentContent(),boardId);
        return "redirect:/board/"+String.valueOf(boardId);
    }

}
