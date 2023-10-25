package org.example.feature.currency;

import org.example.feature.currency.dto.Currency;

import java.util.stream.Stream;

public interface CurrencyService {
    double getRate(Currency currency);
}
