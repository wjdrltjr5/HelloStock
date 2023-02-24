package deu.hellostock.service;

import deu.hellostock.dto.BoardDto;
import deu.hellostock.dto.SessionDto;
import deu.hellostock.entity.Board;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<BoardDto> findAll(){
        return boardRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }
    private BoardDto entityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getBoardid())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .createTime(board.getCreateDate())
                .updateTime(board.getUpdateDate())
                .build();
    }

}
