package deu.hellostock.service;

import deu.hellostock.entity.MyStock;
import deu.hellostock.repository.MyStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyStockService {
    private final MyStockRepository myStockRepository;

    @Transactional
    public void addStock(MyStock myStock){
        myStockRepository.save(myStock);
    }

}
