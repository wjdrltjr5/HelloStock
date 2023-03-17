package deu.hellostock.service;

import deu.hellostock.api.StockAPI;
import deu.hellostock.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockAPI stockAPIService;

    public List<Item> searchStocks(int page, String keyword){
        return stockAPIService.searchStocks(page,keyword);
    }

    public long totalCount(String keyword){
        return stockAPIService.totalCount(keyword);
    }

    public List<Item> getStock(int page, String keyword){
        return stockAPIService.getStock(page,keyword);
    }

}

