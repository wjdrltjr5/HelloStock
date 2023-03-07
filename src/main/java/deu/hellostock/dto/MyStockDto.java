package deu.hellostock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyStockDto {

    private Long id;
    private Long memberid;
    private String stockName;
    private String createDate;
    private String clpr;
}
