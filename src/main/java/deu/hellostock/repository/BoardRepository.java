package deu.hellostock.repository;

import deu.hellostock.entity.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Override
    @EntityGraph(attributePaths = {"member"})
    Optional<Board> findById(Long aLong);
    List<Board> findByTitleContainsOrContentContains(String title,String content);
}
