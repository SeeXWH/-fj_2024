package org.example.lesson_5_spring.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CbrCurrencyRateInitializer implements CommandLineRunner {

    private final CurrencyRateCache currencyRateCache;

    public CbrCurrencyRateInitializer(CurrencyRateCache currencyRateCache) {
        this.currencyRateCache = currencyRateCache;
    }

    @Override
    public void run(String... args){
        currencyRateCache.loadValutesFromCbr();
    }
}