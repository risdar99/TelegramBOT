package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.feature.currency.dto.Currency;
import org.example.feature.currency.dto.CurrencyItemNBU;
import org.example.feature.currency.dto.CurrencyItemPrivat;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivatBankCurrencyService implements CurrencyService{
    String json;
    List<CurrencyItemPrivat> currencyItems;
    String url ="https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    @Override
    public double getRate(Currency currency) {
        // TODO: 30.10.2023 можна розбити на під методи
        getJson(url);

        return currencyItems.stream()
                .filter(it -> it.getCcy().equals(currency))
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyItemPrivat::getBuy)
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
            throw new IllegalStateException("Can`t connect to Mono API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemPrivat.class)
                .getType();
        currencyItems = new Gson().fromJson(json,typeToken);
    }

    @Override
    public int getCurrencyCode(Currency currency) {
        return 0;
    }
}
