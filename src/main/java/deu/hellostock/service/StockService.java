package deu.hellostock.service;

import deu.hellostock.api.StockAPI;
import deu.hellostock.dto.item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockAPI stockAPIService;

    public List<item> searchStocks(int page,String keyword){
        return stockAPIService.searchStocks(page,keyword);
    }
    public List<item> getStock(int page,String keyword){
        return stockAPIService.getStock(page,keyword);
    }

}

