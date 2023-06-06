package deu.hellostock.service;

import deu.hellostock.dto.MemberDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(MemberDTO memberDto){
        String password = passwordEncoder.encode(memberDto.getPassword());
        Member member = Member.builder().username(memberDto.getUsername())
                .nickname(memberDto.getNickname())
                .password(password)
                .role(Role.USER).build();
        memberRepository.save(member);
        return member;
    }
    @Transactional
    public boolean updateNickname(String nickname,String username){
        if(checkUsername(nickname)){
            Member member = memberRepository.findByUsername(username).get();
            member.updateNickname(nickname);
            return true;
        }
        return false;
    }
    public String getNickname(String username){
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("회원 정보 없음")).getNickname();
    }

    public Member findByUserName(String username){
        return memberRepository.findByUsername(username).get();
    }

    @Transactional
    public void deleteMember(HttpSession session){
        Member member = memberRepository.findById(((SessionDTO)session.getAttribute("member")).getMemberid())
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));
        if (member.isOauth2()){
            log.info("access_token = {}",(String)session.getAttribute("access_token"));
            unLinkOauth2((String)session.getAttribute("access_token"));
            memberRepository.delete(member);
        }
        memberRepository.delete(member);
    }

    public boolean checkUsernameDuplication(String username){
        return memberRepository.existsByUsername(username);
    }
    public boolean checkNicknameDuplication(String nickname){
        return memberRepository.existsByNickname(nickname);
    }
    private boolean checkUsername(String nickname){
        Optional<Member> byUsername = memberRepository.findByUsername(nickname);
        return byUsername.isEmpty();
    }
    public boolean isOauth2(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("사용자 없음")).isOauth2();
    }

    private void unLinkOauth2(String access_token){
        URI uri = UriComponentsBuilder.fromUriString("https://nid.naver.com/oauth2.0/token")
                .queryParam("client_id",clientId)
                .queryParam("client_secret",clientSecret)
                .queryParam("access_token",access_token)
                .queryParam("grant_type","delete")
                .build(false)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(uri,String.class);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        log.info("response = {}",response.getStatusCode());
    }

    @Transactional
    public void updatePassword(String password, HttpSession session){
        Member member = memberRepository.findById(((SessionDTO) session.getAttribute("member")).getMemberid()).get();
        member.updatePassword(passwordEncoder.encode(password));
    }
}
