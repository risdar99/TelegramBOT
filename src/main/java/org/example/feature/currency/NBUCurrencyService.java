package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.example.feature.currency.dto.Currency;
import org.example.feature.currency.dto.CurrencyItemMono;
import org.example.feature.currency.dto.CurrencyItemNBU;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NBUCurrencyService implements CurrencyService{
    String json;
    List<CurrencyItemNBU> currencyItems;
    String url ="https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    @Override
    public double getRate(Currency currency) {

        getJson(url);
        int r030 = getCurrencyCode(currency);

        return currencyItems.stream()
                .filter(it -> it.getR030() == (r030))
                .map(CurrencyItemNBU::getRate)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void getJson(String str) {
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t connect to NBU API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemNBU.class)
                .getType();
        currencyItems = new Gson().fromJson(json,typeToken);
    }

    @Override
    public int getCurrencyCode(Currency currency) {
        if(currency == Currency.USD) {
            return 840;
        } else if(currency == Currency.EUR) {
            return 978;
        } else {
            return 0;
        }
    }
}
