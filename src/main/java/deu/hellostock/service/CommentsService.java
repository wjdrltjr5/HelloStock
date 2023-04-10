package deu.hellostock.service;

import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.Board;
import deu.hellostock.entity.Comments;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import deu.hellostock.repository.CommentsRepository;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    public void writeComment(HttpSession session,String content,Long boardId){
        SessionDTO sessionMember = (SessionDTO)session.getAttribute("member");
        Board board = boardRepository.findById(boardId).orElse(null);
        Member member = memberRepository.findById(sessionMember.getMemberid()).get();
        Comments comment = Comments.builder().board(board).member(member).content(content).nickname(member.getNickname()).build();
        commentsRepository.save(comment);
    }
}
