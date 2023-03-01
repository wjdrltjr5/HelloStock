package deu.hellostock.controller;

import deu.hellostock.dto.item;
import deu.hellostock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks")
    public String stocksSearch(@RequestParam(value = "keyword",defaultValue = "")String keyword, @RequestParam(value = "page",defaultValue = "1")int page, Model model){
        List<item> items = stockService.searchStocks(page, keyword);
        model.addAttribute("items",items);
        model.addAttribute("pageNum",page);
        return "/stocks";
    }

}
