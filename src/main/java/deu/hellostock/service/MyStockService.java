package deu.hellostock.service;

import deu.hellostock.dto.MemberDto;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.MyStock;
import deu.hellostock.entity.Stock;
import deu.hellostock.repository.MemberRepository;
import deu.hellostock.repository.MyStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyStockService {
    private final MyStockRepository myStockRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void addStock(MemberDto memberDto, Stock stock){
        Member member = memberRepository.findByUsername(memberDto.getUsername()).orElse(null);
        MyStock myStock = createMyStock(stock, member);
        myStockRepository.save(myStock);
    }
    private static MyStock createMyStock(Stock stock, Member member) {
        return MyStock.builder().stock(stock)
                .member(member).build();
    }
    public List<MyStock> findByMember(Member member){
        return myStockRepository.findByMember(member);
    }
    // dto로 교체하기
    public void deleteMyStock(MyStock myStock){
        myStockRepository.delete(myStock);
    }
}
