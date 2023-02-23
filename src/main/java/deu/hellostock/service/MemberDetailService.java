package deu.hellostock.service;

import deu.hellostock.dto.SessionDto;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;
    // username db에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("해당 사용자가 존재하지 않습니다."));
        session.setAttribute("member",new SessionDto(member));
        return createMember(member);
    }

    private User createMember(Member member){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(() -> member.getRole().toString());
        return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
    }

}
