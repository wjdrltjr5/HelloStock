package deu.hellostock.service;

import deu.hellostock.api.StockCompanyInformationAPI;
import deu.hellostock.dto.CompanyInfo;
import deu.hellostock.entity.Corp;
import deu.hellostock.repository.CorpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


@RequiredArgsConstructor
@Service
public class CompanyInfoService {
    private final CorpRepository corpRepository;
    private final StockCompanyInformationAPI api;
    public void saveCorps(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(xmlFile);

        NodeList nodeList = document.getElementsByTagName("list");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            String corpCode = element.getElementsByTagName("corp_code").item(0).getTextContent();
            String corpName = element.getElementsByTagName("corp_name").item(0).getTextContent();
            String stockCode = element.getElementsByTagName("stock_code").item(0).getTextContent();
            String modifyDate = element.getElementsByTagName("modify_date").item(0).getTextContent();

            Corp corp = Corp.builder().corpCode(corpCode)
                    .corpName(corpName)
                    .stockCode(stockCode)
                    .modifyDate(modifyDate)
                    .build();
            corpRepository.save(corp);
        }
    }
    public CompanyInfo getCompanyInfo(String stockCode){
        Corp corp = corpRepository.findByStockCode(stockCode);
        CompanyInfo companyInfo = api.getCompanyInfo(corp.getCorpCode());
        return companyInfo;
    }
}
