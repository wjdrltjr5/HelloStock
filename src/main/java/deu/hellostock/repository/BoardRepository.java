package deu.hellostock.repository;

import deu.hellostock.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Override
    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitleContainsOrContentContains(String title, String content,Pageable pageable);

}
