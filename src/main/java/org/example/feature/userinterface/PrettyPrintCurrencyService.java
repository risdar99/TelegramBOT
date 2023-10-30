package org.example.feature.userinterface;


import org.example.feature.currency.dto.Currency;

// TODO: 30.10.2023 ніколи не використовується
public class PrettyPrintCurrencyService {
    public String convert (double rate, Currency currency){
        String template = "Курс ${currency} => UAH =${rate}";

        float roundedRate = Math.round(rate * 100d) / 100.f;
        return template
                .replace("${currency}", currency.name())
                .replace("${rate}", roundedRate + " ");
    }
}
