package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Stock {

    String stockName;

    String createDate;

    String clpr;
}
