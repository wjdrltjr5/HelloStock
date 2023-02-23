package deu.hellostock.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDto {
    private String title;
    private String content;
}
