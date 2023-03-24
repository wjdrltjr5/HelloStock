package deu.hellostock.controller;

import deu.hellostock.dto.MyStockDTO;
import deu.hellostock.dto.SessionDTO;
import deu.hellostock.dto.StockDTO;
import deu.hellostock.service.MyStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyStockController {

    private final MyStockService myStockService;

    @PostMapping("/mystock")
    public String addMyStock(StockDTO stockDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionDTO member = (SessionDTO)session.getAttribute("member");
        if (stockDTO.getMemberId() == member.getMemberid()){
            log.info("stockDTO = {}",stockDTO.toString());
            myStockService.addStock(stockDTO);
            return "redirect:/mystock";
        }
        return "redirect:/errorPage";
    }
    @GetMapping("/mystock")
    public String getMystock(HttpServletRequest request, Model model,
                             @RequestParam(value = "page",defaultValue = "1")int page ){
        HttpSession session = request.getSession();
        PageRequest pageRequest = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC,"id"));
        SessionDTO member = (SessionDTO) session.getAttribute("member");
        Page<MyStockDTO> paging = myStockService.getMyStock(member.getMemberid(),pageRequest);
        List<MyStockDTO> myStock = paging.getContent();

        model.addAttribute("myStock",myStock);
        model.addAttribute("previousPage",paging.hasPrevious());
        model.addAttribute("nextPage",paging.hasNext());
        return "/mystock";
    }

    @DeleteMapping("/mystock/{mystockid}")
    public String myStockDelete(@PathVariable("mystockid")long myStockId){
        myStockService.delete(myStockId);
        return "redirect:/mystock";
    }
}

