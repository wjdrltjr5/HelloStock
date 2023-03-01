package deu.hellostock.service;

import deu.hellostock.dto.BoardDto;
import deu.hellostock.dto.SessionDto;
import deu.hellostock.entity.Board;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public Page<BoardDto> findAll(Pageable pageable){
        return boardRepository.findAll(pageable).map(this::entityToDto);
    }
    public BoardDto findBoard(Long boardId){
        return boardRepository.findById(boardId).map(this::entityToDto).orElseThrow(() ->
                new RuntimeException("게시글이 존재하지 않습니다."));
    }
    @Transactional
    public void update(Long boardId,BoardDto boardDto){
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new RuntimeException("게시글이 존재하지 않습니다."));
        board.update(boardDto);
    }
    @Transactional
    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }
    public Page<BoardDto> search(String title,String content,Pageable pageable){
        return boardRepository.findByTitleContainsOrContentContains(title,content,pageable).map(this::entityToDto);
    }

    private BoardDto entityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getBoardid())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .memberid(board.getMember().getId())
                .createTime(board.getCreateDate())
                .updateTime(board.getUpdateDate())
                .build();
    }

}
