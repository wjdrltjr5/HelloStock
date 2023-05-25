package deu.hellostock.repository;

import deu.hellostock.entity.Board;
import deu.hellostock.entity.Like;
import deu.hellostock.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    @Query("select count(*) from Like l where l.likeId.board = :board")
    int countLike(@Param("board") Board board);
}
