package deu.hellostock.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class CommentDTO {
    @NotBlank
    private String commentContent;
    private String nickname;
    private LocalDateTime time;
    private Long memberId;
}
