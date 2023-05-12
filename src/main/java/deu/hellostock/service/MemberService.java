package deu.hellostock.service;

import deu.hellostock.dto.MemberDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

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

    public boolean usernameDuplicationCheck(String username){
        return memberRepository.existsByUsername(username);
    }

    private boolean checkUsername(String nickname){
        Optional<Member> byUsername = memberRepository.findByUsername(nickname);
        return byUsername.isEmpty();
    }
}
