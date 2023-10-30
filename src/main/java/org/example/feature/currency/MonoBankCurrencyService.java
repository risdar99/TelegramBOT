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
    String json;
    List<CurrencyItemMono> currencyItems;
    String url ="https://api.monobank.ua/bank/currency";
    @Override
    public double getRate(Currency currency){

        getJson(url);
        int currencyCode = getCurrencyCode(currency);

        return currencyItems.stream()
                .filter(it -> it.getCurrencyCodeA() == (currencyCode))
                .filter(it -> it.getCurrencyCodeB() == 980)
                .map(CurrencyItemMono::getRateBuy)
                .findFirst()
                .orElseThrow();

    }

    public int getCurrencyCode(Currency currency) {
        if(currency == Currency.USD) {
            return 840;
        } else if(currency == Currency.EUR) {
            return 978;
        } else {
            return 0;
        }
    }

    public void getJson(String url){
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
        currencyItems = new Gson().fromJson(json,typeToken);
    }
}
