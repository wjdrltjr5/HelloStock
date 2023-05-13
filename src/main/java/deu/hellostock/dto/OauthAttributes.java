package deu.hellostock.dto;

import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OauthAttributes {
    private final Map<String, Object> attributes;
    private final String nickname;
    private final String email;
    private final String nameAttributeKey;

    public static OauthAttributes of(String registrationID, String userNameAttributeName, Map<String,Object> attributes){
        if("naver".equals(registrationID)){
            return ofNaver("id",attributes);
        }
        return ofNaver("id",attributes);
    }

    private static OauthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OauthAttributes.builder()
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(response)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .username(this.email)
                .nickname(this.nickname)
                .oauth2(true)
                .role(Role.USER)
                .build();
    }

}
