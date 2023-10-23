package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.feature.currency.dto.Currency;
import org.example.feature.currency.dto.CurrencyItemNBU;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

public class NBUCurrencyService implements CurrencyService{

    @Override
    public double getRate(Currency currency) {
        String url ="https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
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
            throw new IllegalStateException("Can`t connect to NBU API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemNBU.class)
                .getType();
        List<CurrencyItemNBU> currencyItemsNBU = new Gson().fromJson(json,typeToken);

        int r030;
        if(currency == Currency.USD) {
            r030 = 840;
        } else if(currency == Currency.EUR) {
            r030 = 978;
        } else {
            r030 = 0;
        }

        return currencyItemsNBU.stream()
                .filter(it -> it.getR030() == (r030))
                .map(CurrencyItemNBU::getRate)
                .findFirst()
                .orElseThrow();
    }
}
