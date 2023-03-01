package deu.hellostock.repository;

import deu.hellostock.entity.Member;
import deu.hellostock.entity.MyStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyStockRepository extends JpaRepository<MyStock,Long> {

    List<MyStock> findByMember(Member member);
}
