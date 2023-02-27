package deu.hellostock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class StockAPIService {
    private final String key = "bwqCbbK0DeKwzuy7C%2F5VPjQqMx7ZtfALYYC2sdz92Y0zcRVgdcHblbIqNNNOKzZ7rB%2BISH26xbEI9%2Bh%2F5D17MA%3D%3D";

    public String getStocks(int page, String stock){
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1160100/service/GetStockSecuritiesInfoService")
                .queryParam("serviceKey",key)
                .queryParam("numOfRows",10)
                .queryParam("pageNo",1)
                .queryParam("resultType","json")
                .queryParam("itmsNm",stock)
                .encode()
                .build()
                .toUri();
            log.info("uri={}",uri.toString());

        RestTemplate restTemplate = new RestTemplate();  //entity로 보내고 상태코드 받고 내부정보만 가져올까?
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

}
