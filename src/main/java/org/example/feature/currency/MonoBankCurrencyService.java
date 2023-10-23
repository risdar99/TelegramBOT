package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.feature.currency.dto.Currency;
import org.example.feature.currency.dto.CurrencyItem;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

public class MonoBankCurrencyService implements CurrencyService{
    @Override
    public double getRate(Currency currency){
        String url ="https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
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
                .getParameterized(List.class, CurrencyItem.class)
                .getType();
        List<CurrencyItem> currencyItems = new Gson().fromJson(json,typeToken);

        return currencyItems.stream()
                .filter(it -> it.getCcy().equals(currency))
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyItem::getBuy)
                .findFirst()
                .orElseThrow();

    }
}
