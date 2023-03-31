package deu.hellostock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyInfo {
    private String status;
    private String message;
    private String corp_code;
    private String corp_name;
    private String corp_name_eng;
    private String stock_name;
    private String stock_code;
    private String ceo_nm;
    private String corp_cls;
    private String jurir_no;
    private String bizr_no;
    private String adres;
    private String hm_url;
    private String ir_url;
    private String phn_no;
    private String fax_no;
    private String induty_code;
    private String est_dt;
    private String acc_mt;
}
