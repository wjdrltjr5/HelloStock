package deu.hellostock.repository;

import deu.hellostock.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

}
