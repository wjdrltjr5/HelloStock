package deu.hellostock.api;

import deu.hellostock.dto.StockResponse;
import deu.hellostock.dto.Item;
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
public class StockAPI {

    private final String key = "bwqCbbK0DeKwzuy7C%2F5VPjQqMx7ZtfALYYC2sdz92Y0zcRVgdcHblbIqNNNOKzZ7rB%2BISH26xbEI9%2Bh%2F5D17MA%3D%3D";

    public List<Item> searchStocks(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("pageNo",page)
                .queryParam("resultType","json")
                .queryParam("likeItmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();

        StockResponse result = getStockResponse(uri);
        List<Item> items = result.getResponse().getBody().getItems().getItem();
        return items;
    }

    private static StockResponse getStockResponse(URI uri) {
        RestTemplate restTemplate = new RestTemplate();
        StockResponse result = restTemplate.getForObject(uri,StockResponse.class);
        return result;
    }

    public List<Item> getStock(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("pageNo",1)
                .queryParam("resultType","json")
                .queryParam("itmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();

        StockResponse result = getStockResponse(uri);
        List<Item> items = result.getResponse().getBody().getItems().getItem();
        return items;
    }

    public long totalCount(String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("resultType","json")
                .queryParam("likeItmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();
        StockResponse result = getStockResponse(uri);
        return result.getResponse().getBody().getTotalCount();
    }
}
