package org.example.lesson_5_spring.components;

import org.example.lesson_5_spring.exeption.CurrenciesExeption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test") // Используйте отдельный профиль для тестов
public class CurrencyRateCacheIntegrationTest {

    @Autowired
    private CurrencyRateCache currencyRateCache;

    @Test
    void testLoadValutesFromCbr_serviceUnavailable_throwsCurrenciesExeption() throws InterruptedException {
        for (int i = 0; i < 110; i++) {
            try {
                currencyRateCache.loadValutesFromCbr();
            } catch (CurrenciesExeption e) {
            }
            Thread.sleep(100);
        }

        assertThrows(CurrenciesExeption.class, currencyRateCache::loadValutesFromCbr);
    }
}