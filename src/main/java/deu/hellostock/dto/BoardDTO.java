package deu.hellostock.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BoardDTO {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String nickname;
    private Long memberid;

}
