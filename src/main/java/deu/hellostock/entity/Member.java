package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends TimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 20,unique = true)
    private String username;
    @Column
    private String password;
    @Column(length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

}
