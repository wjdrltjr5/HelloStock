package deu.hellostock.api;

import deu.hellostock.dto.CompanyInfo;
import deu.hellostock.dto.Item;
import deu.hellostock.dto.StockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
@Service
@Slf4j
public class StockCompanyInformaionAPI {


    @Value("${company.api.key}")
    private String key;

    public CompanyInfo getCompanyInfo(String corpCode){
        URI uri = UriComponentsBuilder.fromUriString("https://opendart.fss.or.kr/api/company.json")
                .queryParam("crtfc_key",key)
                .queryParam("corp_code",URLEncoder.encode(corpCode,StandardCharsets.UTF_8))
                .build(false)
                .toUri();


        RestTemplate restTemplate = new RestTemplate();
        CompanyInfo companyInfo = restTemplate.getForObject(uri, CompanyInfo.class);
        log.info("for entity = {}",companyInfo.getStock_name());
        return companyInfo;
    }
}
