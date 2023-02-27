package deu.hellostock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class StockAPIService {
    private final String key = "bwqCbbK0DeKwzuy7C%2F5VPjQqMx7ZtfALYYC2sdz92Y0zcRVgdcHblbIqNNNOKzZ7rB%2BISH26xbEI9%2Bh%2F5D17MA%3D%3D";
    public void getStocks(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",1)
                .queryParam("pageNo",1)
                .queryParam("resultType","json")
//                .queryParam("likeSrtnCd",stockNum)
                .queryParam("itmsNm",URLEncoder.encode(stock,StandardCharsets.UTF_8))
                .build(true)
                .toUri();
            log.info("uri={}",uri);

        RestTemplate restTemplate = new RestTemplate();  //entity로 보내고 상태코드 받고 내부정보만 가져올까?
        String result = restTemplate.getForObject(uri, String.class);
        log.info("주식 result = {}",result);
//        return result;
    }

}
