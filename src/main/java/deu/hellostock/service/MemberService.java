package deu.hellostock.service;

import deu.hellostock.dto.MemberDto;
import deu.hellostock.entity.Member;
import deu.hellostock.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(MemberDto memberDto){
        String password = passwordEncoder.encode(memberDto.getPassword());
        Member member = Member.builder().username(memberDto.getUsername())
                .nickname(memberDto.getNickname())
                .password(password).build();
        memberRepository.save(member);
        return member;
    }
}
