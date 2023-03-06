package deu.hellostock.controller;

import deu.hellostock.dto.item;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks")
    public String stocks(@RequestParam(value = "page",defaultValue = "1")int page, Model model){
        List<item> items = stockService.searchStocks(page, "");
        model.addAttribute("items",items);
        model.addAttribute("pageNum",page);
        return "/stocks";
    }
    @GetMapping("/stocks/search")
    public String stocksSearch(@RequestParam(value = "q",defaultValue = "")String keyword, @RequestParam(value = "page",defaultValue = "1")int page, Model model){
        List<item> items = stockService.searchStocks(page, keyword);
        model.addAttribute("items",items);
        model.addAttribute("pageNum",page);
        return "/stocks";
    }
    @GetMapping("/stock/{stockname}")
    public String stock(@PathVariable("stockname")String stockName,Model model){
        List<item> stocks = stockService.getStock(1, stockName);
        ArrayList<String> labels = stocks.stream().map(item::getBasDt).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> datas = stocks.stream().map(item::getClpr).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(labels);
        Collections.reverse(datas);
        model.addAttribute("stocks",stocks);
        model.addAttribute("labels",labels);
        model.addAttribute("data",datas);
        return "stock";
    }

}
