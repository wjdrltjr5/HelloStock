package deu.hellostock.service;

import deu.hellostock.api.NaverNewsAPI;
import deu.hellostock.api.StockAPI;
import deu.hellostock.dto.Item;
import deu.hellostock.dto.NewsItem;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockAPI stockAPIService;
    private final NaverNewsAPI newsAPIService;
    @Cacheable(value = "searchStocks")
    public List<Item> searchStocks(int page, String keyword){
        return stockAPIService.searchStocks(page,keyword);
    }
    @Cacheable(value = "totalCount")
    public long totalCount(String keyword){
        return stockAPIService.totalCount(keyword);
    }

    public List<Item> getStock(int page, String keyword){
        return stockAPIService.getStock(page,keyword);
    }
    public List<NewsItem> getNewsItem(String keyword){
        return newsAPIService.getNewsItem(keyword);
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

