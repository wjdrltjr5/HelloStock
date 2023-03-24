package deu.hellostock.service;

import deu.hellostock.dto.MyStockDTO;
import deu.hellostock.dto.StockDTO;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.MyStock;
import deu.hellostock.entity.Stock;
import deu.hellostock.repository.MemberRepository;
import deu.hellostock.repository.MyStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MyStockService {
    private final MyStockRepository myStockRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void addStock(StockDTO stock){
        Member member = memberRepository.findById(stock.getMemberId())
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다."));
        MyStock myStock = createMyStock(stock, member);
        myStockRepository.save(myStock);
    }
    private static MyStock createMyStock(StockDTO stockDTO, Member member) {
        Stock stock = Stock.builder().stockName(stockDTO.getStockName())
                .createDate(stockDTO.getDate())
                .clpr(stockDTO.getClpr()).build();
        return MyStock.builder().stock(stock)
                .member(member).build();
    }
    public Page<MyStockDTO> getMyStock(Long memberid, Pageable pageable){
        Member member = memberRepository.findById(memberid).get();
        return myStockRepository.findByMember(member,pageable).map(this::entityToDto);
    }
    // dto로 교체하기
    @Transactional
    public void delete(long myStockId){
        log.info("myStockId = {}",myStockId);
        myStockRepository.deleteById(myStockId);
    }
    private MyStockDTO entityToDto(MyStock stock){
        return MyStockDTO.builder().stockName(stock.getStock().getStockName())
                .id(stock.getId())
                .createDate(stock.getStock().getCreateDate())
                .clpr(stock.getStock().getClpr())
                .build();
    }
}
