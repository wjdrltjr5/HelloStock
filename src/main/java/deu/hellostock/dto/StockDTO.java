package deu.hellostock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class StockDTO {

    private long memberId;
    private String stockName;
    private String date;
    private String clpr;

}
