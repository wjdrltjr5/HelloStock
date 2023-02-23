package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Board extends TimeEntity {

    @Id @GeneratedValue
    private Long boardid;


    private String content;

    private String title;

    @ManyToOne
    @JoinColumn(name = "memberid")
    private Member member;

    private String nickname;


}
