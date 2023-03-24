package deu.hellostock.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class MemberDTO {
    @NotBlank(message = "아이디를 입력하세요")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8,max = 20)
    private String password;
    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;
}

