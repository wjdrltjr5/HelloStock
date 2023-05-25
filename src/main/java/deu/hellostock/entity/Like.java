package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LIKE_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Like {

    @EmbeddedId
    private LikeId likeId;

    @Column(name = "double_check")
    private boolean check;
}
