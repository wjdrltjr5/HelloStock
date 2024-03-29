package deu.hellostock.repository;

import deu.hellostock.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findByTitleContainsOrContentContains(String title, String content,Pageable pageable);

}
