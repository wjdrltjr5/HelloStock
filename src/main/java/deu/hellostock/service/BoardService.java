package deu.hellostock.service;

import deu.hellostock.dto.BoardDto;
import deu.hellostock.dto.SessionDto;
import deu.hellostock.entity.Board;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;
    @Transactional
    public void write(BoardDto boardDto, SessionDto sessionDto){
        Member member = memberService.findByUserName(sessionDto.getUsername());
        Board board = Board.builder().title(boardDto.getTitle())
                .content(boardDto.getContent())
                .nickname(member.getNickname())
                .member(member).build();
        boardRepository.save(board);
    }
}
