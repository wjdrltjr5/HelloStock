package deu.hellostock.service;

import deu.hellostock.entity.Board;
import deu.hellostock.entity.Like;
import deu.hellostock.entity.LikeId;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.BoardRepository;
import deu.hellostock.repository.LikeRepository;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    public int getLikeCount(Long boardId){
        Board board = boardRepository.getById(boardId);
        return likeRepository.countLike(board);
    }
    @Transactional
    public String clickLike(Long boardId,Long memberId){
        Member member = memberRepository.findById(memberId).get();
        Board board = boardRepository.findById(boardId).get();
        LikeId likeId = new LikeId(member, board);
        Like like = likeRepository.findById(likeId).orElse(null);
        if(like == null){
            likeRepository.save(Like.builder().likeId(likeId).build());
            return "추천되었습니다.";
        } else {
            return "이미 추천한 게시글입니다.";
        }
    }

}
