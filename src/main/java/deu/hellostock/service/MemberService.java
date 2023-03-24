package deu.hellostock.service;

import deu.hellostock.dto.MemberDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.Role;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Member findByUserName(String username){
        return memberRepository.findByUsername(username).get();
    }
}
