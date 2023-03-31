package deu.hellostock.repository;

import deu.hellostock.entity.Corp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpRepository extends JpaRepository<Corp, String> {
}
