package deu.hellostock.controller;

import deu.hellostock.dto.Item;
import deu.hellostock.service.MyStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyStockController {

    private final MyStockService myStockService;

    @GetMapping("/mystock/{username}")
    public String addMyStock(Item item, @PathVariable("username")String username){
        return "";
    }

}

