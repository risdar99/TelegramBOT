package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.example.feature.currency.dto.Currency;
import org.example.feature.currency.dto.CurrencyItemMono;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MonoBankCurrencyService implements CurrencyService{
    @Override
    public double getRate(Currency currency){
        String url ="https://api.monobank.ua/bank/currency";
        String json;
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can`t connect to Mono API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemMono.class)
                .getType();
        List<CurrencyItemMono> currencyItems = new Gson().fromJson(json,typeToken);

        int currencyCode;
        if(currency == Currency.USD) {
            currencyCode = 840;
        } else if(currency == Currency.EUR) {
            currencyCode = 978;
        } else {
            currencyCode = 0;
        }

        return currencyItems.stream()
                .filter(it -> it.getCurrencyCodeA() == (currencyCode))
                .filter(it -> it.getCurrencyCodeB() == 980)
                .map(CurrencyItemMono::getRateBuy)
                .findFirst()
                .orElseThrow();

    }
}
