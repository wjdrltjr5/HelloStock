package deu.hellostock.controller;

import deu.hellostock.dto.Item;
import deu.hellostock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks")
    public String stocks(@RequestParam(value = "page",defaultValue = "1")int page, Model model){
        List<Item> items = stockService.searchStocks(page, "");
        long totalCount = stockService.totalCount("");
        double lastPage = Math.ceil(totalCount / 10);
        model.addAttribute("items",items);
        model.addAttribute("pageNum",page);
        model.addAttribute("lastPage",lastPage);
        return "stocks";
    }
    @GetMapping("/stocks/search")
    public String stocksSearch(@RequestParam String keyword, @RequestParam(value = "page",defaultValue = "1")int page, Model model){
        List<Item> items = stockService.searchStocks(page, keyword);
        long totalCount = stockService.totalCount(keyword);
        double lastPage = Math.ceil(totalCount / 10);
        log.info("totalCount = {} lastPage = {}",totalCount,lastPage);
        model.addAttribute("items",items);
        model.addAttribute("pageNum",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("lastPage",lastPage);
        return "stocks-search";
    }
    @GetMapping("/stock/{stockname}")
    public String stock(@PathVariable("stockname")String stockName,Model model){
        List<Item> stocks = stockService.getStock(1, stockName);

        ArrayList<String> labels = stocks.stream().map(Item::getBasDt).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> datas = stocks.stream().map(Item::getClpr).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(labels);
        Collections.reverse(datas);
        model.addAttribute("stocks",stocks);
        model.addAttribute("labels",labels);
        model.addAttribute("data",datas);
        return "stock";
    }

}
