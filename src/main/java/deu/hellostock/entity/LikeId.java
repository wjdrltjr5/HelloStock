package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LikeId  implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardid")
    private Board board;
}
