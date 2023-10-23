package org.example.feature.userinterface;

import org.example.Main;
import org.example.feature.currency.dto.Currency;

public class PrettyPrintCurrencyService {
    public String convert (double rate, Currency currency){
        String template = "Курс ${currency} => UAH =${rate}";

        float roundedRate = Math.round(rate * 100d) / 100.f;
        return template
                .replace("${currency}", currency.name())
                .replace("${rate}", roundedRate + " ");
    }
}
