package org.example.feature.currency.dto;

import lombok.Data;

@Data
public class CurrencyItemMono {
    private int currencyCodeA;
    private int currencyCodeB;
    private long date;
    /*private Currency ccy;
    private Currency base_ccy;*/
    private float rateBuy;
    private float rateCross;
    private float rateSale;
}
