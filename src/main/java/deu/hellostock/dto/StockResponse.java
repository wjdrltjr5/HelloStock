package deu.hellostock.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class StockResponse {

    Response response;

    @Data
    private static class Header{
            private String resultCode;
            private String resultMsg;
    }
    @Data
    public static class Body{
        private int numOfRows;
        private int pageNo;
        private int totalCount;
        private items items;
    }
    @Data
    public static class Response{
        private Header header;
        private Body body;
    }
    @Data
    public static class items{
        List<Item> item = new ArrayList<>();
    }
}
