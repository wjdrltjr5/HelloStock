package deu.hellostock.service;

import deu.hellostock.api.StockAPI;
import deu.hellostock.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public Map<String,ArrayList<String>> getStockData(int page, String keyword){
        List<Item> stocks = stockAPIService.getStock(page, keyword);
        ArrayList<String> labels = stocks.stream().map(Item::getBasDt).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> data = stocks.stream().map(Item::getClpr).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(labels);
        Collections.reverse(data);
        Map<String,ArrayList<String>> stockData = new HashMap<>();
        stockData.put("labels",labels);
        stockData.put("data",data);
        return stockData;
    }
}

