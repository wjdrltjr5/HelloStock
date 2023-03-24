package deu.hellostock.service;

import deu.hellostock.dto.BoardDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.Board;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void write(BoardDTO boardDto, SessionDTO sessionDto){
        Member member = memberService.findByUserName(sessionDto.getUsername());
        Board board = Board.builder().title(boardDto.getTitle())
                .content(boardDto.getContent())
                .nickname(member.getNickname())
                .member(member).build();
        boardRepository.save(board);
    }

    public Page<BoardDTO> findAll(Pageable pageable){
        return boardRepository.findAll(pageable).map(this::entityToDto);
    }
    public BoardDTO findBoard(Long boardId){
        return boardRepository.findById(boardId).map(this::entityToDto).orElseThrow(() ->
                new RuntimeException("게시글이 존재하지 않습니다."));
    }
    @Transactional
    public void update(Long boardId, BoardDTO boardDto){
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new RuntimeException("게시글이 존재하지 않습니다."));
        board.update(boardDto);
    }
    @Transactional
    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }
    public Page<BoardDTO> search(String title, String content, Pageable pageable){
        return boardRepository.findByTitleContainsOrContentContains(title,content,pageable).map(this::entityToDto);
    }

    private BoardDTO entityToDto(Board board) {
        return BoardDTO.builder()
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
