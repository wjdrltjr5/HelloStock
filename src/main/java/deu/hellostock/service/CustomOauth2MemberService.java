package deu.hellostock.service;

import deu.hellostock.dto.OauthAttributes;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession session;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        log.info("member 정보 = {}",member.getUsername());
        session.setAttribute("member", new SessionDTO(member));
        log.info("attributest = {} , nameAttriby key = {}",attributes.getAttributes(),attributes.getNameAttributeKey());
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_"+member.getRole())),
                attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OauthAttributes attributes) {
        Member member = memberRepository.findByUsername(attributes.getEmail()).orElse(attributes.toEntity());
        memberRepository.save(member);
        return member;
    }

}
