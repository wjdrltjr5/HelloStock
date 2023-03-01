package deu.hellostock.service;

import deu.hellostock.dto.item;
import deu.hellostock.entity.Member;
import deu.hellostock.entity.MyStock;
import deu.hellostock.repository.MyStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final MyStockRepository stockRepository;
    private final StockAPIService stockAPIService;

    @Transactional
    public void addStock(MyStock myStock){
        stockRepository.save(myStock);
    }
    public List<MyStock> findByMember(Member member){
        return stockRepository.findByMember(member);
    }

    public List<item> searchStocks(int page,String keyword){
        return stockAPIService.searchStocks(page,keyword);
    }
    public List<item> getStock(int page,String keyword){
        return stockAPIService.getStock(page,keyword);
    }

}

