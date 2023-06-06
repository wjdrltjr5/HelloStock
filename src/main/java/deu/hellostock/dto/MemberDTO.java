package deu.hellostock.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberDTO {
    @NotBlank(message = "아이디를 입력하세요")
    @Email(message = "아이디는 이메일 형식 이어야 합니다.")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;
    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;
}

