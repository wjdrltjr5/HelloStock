package deu.hellostock.dto;

import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
@Getter
@Builder
@AllArgsConstructor
public class SessionDto implements Serializable {
    private Long memberid;
    private String username;
    private String nickname;
    private String password;
    private Role role;

    public SessionDto(Member member) {
        this.memberid = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
