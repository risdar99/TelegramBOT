package org.example.feature.currency.dto;

import lombok.Data;

@Data
public class CurrencyItemNBU {
    private int r030;
    private String txt;
    private Currency cc;
    //private Currency base_cc;
    private float rate;
    private String exchangedate;
}
