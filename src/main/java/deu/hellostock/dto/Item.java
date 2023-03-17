package deu.hellostock.dto;

import lombok.Data;

@Data
public class Item {
    private String basDt; // 기준일자
    private String srtnCd; // 단축코드(종목코드보다 짦으면서 유일성이 보장되는 코드)
    private String isinCd; //isin코드
    private String itmsNm; // 종목명
    private String mrktCtg;  // 상장시장
    private String clpr;   //종가
    private String vs;    // 전일대비 등락
    private String fltRt; //전일대비 등락비율
    private String mkp;  // 시가
    private String hipr;  // 고가
    private String lopr; // 저가
    private String trqu; // 체결수량의 누적합계
    private String trPrc; //거래건 별 체결가격 * 체결수량의 누적합계
    private String lstgStCnt;  //상장주식수
    private String mrktTotAmt;  //시가총액

}
