package deu.hellostock.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NaverNewsResponse {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<NewsItem> items = new ArrayList<>();
}
