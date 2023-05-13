package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private boolean oauth2;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true ,mappedBy = "member")
    private List<MyStock> myStocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "member")
    private List<Comments> comments = new ArrayList<>();

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
    public void updatePassword(String password){
        this.password = password;
    }
}
