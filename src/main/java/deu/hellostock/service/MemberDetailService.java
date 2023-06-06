package deu.hellostock.service;

import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.exception.OAuth2MemberNormalLoginException;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
@Slf4j
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;
    // username db에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 계정입니다."));
        if (member.getPassword() == null){
            throw new OAuth2MemberNormalLoginException("소셜 사용자 입니다. 소셜로그인을 이용해 주세요");
        }
        if(member != null){
            session.setAttribute("member", new SessionDTO(member));
        }
        return new MemberDetails(member);
    }


}
