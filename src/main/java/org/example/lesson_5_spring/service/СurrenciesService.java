package org.example.lesson_5_spring.service;

import lombok.AllArgsConstructor;
import org.example.lesson_5_spring.components.CurrencyRateCache;
import org.example.lesson_5_spring.dto.CurrenciesAnswerPostDto;
import org.example.lesson_5_spring.dto.CurrenciesGetDto;
import org.example.lesson_5_spring.dto.CurrenciesRequestPostDto;
import org.example.lesson_5_spring.exeption.CurrenciesExeption;
import org.example.lesson_5_spring.model.Valute;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Map;

@AllArgsConstructor
@Service
public class СurrenciesService {

    private final CurrencyRateCache currencyRateCache;

    public CurrenciesGetDto getCurrencies(String currency) {
        Map<String, Valute> map = currencyRateCache.loadValutesFromCbr();
        if (!isInvalidCurrency(currency)) {
            if (map.containsKey(currency)) {
                CurrenciesGetDto currencies = new CurrenciesGetDto();
                currencies.setCurrency(currency);
                currencies.setRate(map.get(currency).getValue());
                return currencies;
            } else {
                throw new CurrenciesExeption("Currency not found");
            }
        } else {
            throw new CurrenciesExeption("incorrect currency");
        }
    }

    public CurrenciesAnswerPostDto convertCurrency(CurrenciesRequestPostDto request) {
        if (request.getAmount() == 0 || request.getFromCurrency() == null || request.getToCurrency() == null) {
            throw new CurrenciesExeption("not all parameters were passed");
        }
        double amount = request.getAmount();
        if (amount <= 0) {
            throw new CurrenciesExeption("amount must be greater than 0");
        }
        String fromCurrency = request.getFromCurrency();
        String toCurrency = request.getToCurrency();

        if (!isInvalidCurrency(fromCurrency) && !isInvalidCurrency(toCurrency)) {
            Map<String, Valute> map = currencyRateCache.loadValutesFromCbr();
            if ((map.containsKey(fromCurrency) || fromCurrency.equals("RUB")) && (map.containsKey(toCurrency) || toCurrency.equals("RUB"))) {
                double firsValue = fromCurrency.equals("RUB") ? 1 : Double.parseDouble(currencyRateCache.loadValutesFromCbr().get(fromCurrency).getValue().replace(",", "."));
                double secondValue = toCurrency.equals("RUB") ? 1 : Double.parseDouble(currencyRateCache.loadValutesFromCbr().get(toCurrency).getValue().replace(",", "."));
                double convertedAmount = firsValue * amount / secondValue;
                return new CurrenciesAnswerPostDto(fromCurrency, toCurrency, convertedAmount);
            } else {
                throw new CurrenciesExeption("Currency not found");
            }
        } else {
            throw new CurrenciesExeption("incorrect currency");
        }

    }

    private boolean isInvalidCurrency(String code) {
        try {
            Currency.getInstance(code);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

}
