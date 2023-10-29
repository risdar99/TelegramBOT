package org.example.feature.currency;


import org.example.feature.currency.dto.Currency;

public interface CurrencyService {
    double getRate(Currency currency);
}
