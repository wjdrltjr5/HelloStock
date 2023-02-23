package deu.hellostock.repository;

import deu.hellostock.entity.MyStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStockRepository extends JpaRepository<MyStock,Long> {
}
