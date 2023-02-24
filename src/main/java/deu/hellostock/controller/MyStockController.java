package deu.hellostock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyStockController {

    @GetMapping("/stock")
    public String stock(){
        return "/stock";
    }
}
