package deu.hellostock.dto;

import deu.hellostock.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String nickname;

}
