package deu.hellostock.repository;

import deu.hellostock.entity.Board;
import deu.hellostock.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Long> {

    Page<Comments> findByBoard(Pageable pageable, Board board);

}
