package deu.hellostock.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {
    @NotNull
    private String username;
    @NotNull
    @Size(min = 8,max = 20)
    private String password;
    @NotNull
    private String nickname;
}

