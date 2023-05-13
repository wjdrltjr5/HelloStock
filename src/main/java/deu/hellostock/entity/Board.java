package deu.hellostock.entity;

import deu.hellostock.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class Board extends TimeEntity {

    @Id @GeneratedValue
    private Long boardid;


    private String content;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "board")
    private List<Comments> comments = new ArrayList<>();

    @Column(unique = true)
    private String nickname;

    public void update(BoardDTO boardDto){
        this.content = boardDto.getContent();
    }

}
