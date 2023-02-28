package deu.hellostock.service;

import deu.hellostock.dto.StockResponse;
import deu.hellostock.dto.item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class StockAPIService {

    private final String key = "bwqCbbK0DeKwzuy7C%2F5VPjQqMx7ZtfALYYC2sdz92Y0zcRVgdcHblbIqNNNOKzZ7rB%2BISH26xbEI9%2Bh%2F5D17MA%3D%3D";

    public List<item> searchStocks(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("pageNo",1)
                .queryParam("resultType","json")
                .queryParam("likeItmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();
            log.info("uri={}",uri);

        RestTemplate restTemplate = new RestTemplate();
        StockResponse result = restTemplate.getForObject(uri,StockResponse.class);
        List<item> items = result.getResponse().getBody().getItems().getItem();

//        for (deu.hellostock.dto.item item : items) {
//            log.info("주식 result = {}",item);
//        }
        return items;
    }
    public List<item> getStocks(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("pageNo",1)
                .queryParam("resultType","json")
                .queryParam("likeItmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();
        log.info("uri={}",uri);

        RestTemplate restTemplate = new RestTemplate();
        StockResponse result = restTemplate.getForObject(uri,StockResponse.class);
        List<item> items = result.getResponse().getBody().getItems().getItem();

        for (deu.hellostock.dto.item item : items) {
            log.info("주식 result = {}",item);
        }
        return items;
    }

}
