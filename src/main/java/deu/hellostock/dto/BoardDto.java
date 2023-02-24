package deu.hellostock.dto;

import deu.hellostock.entity.Board;
import deu.hellostock.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BoardDto {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String nickname;
    private Member member;

}
