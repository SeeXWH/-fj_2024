package org.example.lesson_5_spring.service;

import org.example.lesson_5_spring.components.CurrencyRateCache;
import org.example.lesson_5_spring.dto.CurrenciesAnswerPostDto;
import org.example.lesson_5_spring.dto.CurrenciesGetDto;
import org.example.lesson_5_spring.dto.CurrenciesRequestPostDto;
import org.example.lesson_5_spring.exeption.CurrenciesExeption;
import org.example.lesson_5_spring.model.Valute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class СurrenciesServiceTest {

    @Mock
    private CurrencyRateCache currencyRateCache;

    @InjectMocks
    private СurrenciesService currenciesService;

    @Test
    void testGetCurrencies_validCurrency_returnsCurrenciesGetDto() {
        String currencyCode = "USD";
        Valute valute = new Valute();
        valute.setValue("75,0000"); // Пример значения курса
        Map<String, Valute> mockValutes = new HashMap<>();
        mockValutes.put(currencyCode, valute);
        when(currencyRateCache.loadValutesFromCbr()).thenReturn(mockValutes);

        CurrenciesGetDto result = currenciesService.getCurrencies(currencyCode);

        assertNotNull(result);
        assertEquals(currencyCode, result.getCurrency());
        assertEquals("75,0000", result.getRate());
    }

    @Test
    void testGetCurrencies_invalidCurrency_throwsCurrenciesExeption() {
        String currencyCode = "INVALID";

        assertThrows(CurrenciesExeption.class, () -> currenciesService.getCurrencies(currencyCode));
    }

    @Test
    void testGetCurrencies_currencyNotFound_throwsCurrenciesExeption() {
        String currencyCode = "EUR";
        Map<String, Valute> mockValutes = new HashMap<>();
        when(currencyRateCache.loadValutesFromCbr()).thenReturn(mockValutes);

        assertThrows(CurrenciesExeption.class, () -> currenciesService.getCurrencies(currencyCode));
    }

    @Test
    void testConvertCurrency_validRequest_returnsCurrenciesAnswerPostDto() {
        CurrenciesRequestPostDto request = new CurrenciesRequestPostDto();
        request.setFromCurrency("USD");
        request.setToCurrency("RUB");
        request.setAmount(100.0);

        Valute usdValute = new Valute();
        usdValute.setValue("75,0000");
        Map<String, Valute> mockValutes = new HashMap<>();
        mockValutes.put("USD", usdValute);
        when(currencyRateCache.loadValutesFromCbr()).thenReturn(mockValutes);

        CurrenciesAnswerPostDto result = currenciesService.convertCurrency(request);

        assertNotNull(result);
        assertEquals("USD", result.getFromCurrency());
        assertEquals("RUB", result.getToCurrency());
        assertEquals(7500.0, result.getConvertedAmount(), 0.001);
    }

    @Test
    void testConvertCurrency_missingParameters_throwsCurrenciesExeption() {
        CurrenciesRequestPostDto request1 = new CurrenciesRequestPostDto();
        request1.setFromCurrency("USD");
        request1.setAmount(100.0);

        CurrenciesRequestPostDto request2 = new CurrenciesRequestPostDto();
        request2.setToCurrency("RUB");
        request2.setAmount(100.0);

        CurrenciesRequestPostDto request3 = new CurrenciesRequestPostDto();
        request3.setFromCurrency("USD");
        request3.setToCurrency("RUB");

        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request1));
        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request2));
        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request3));
    }

    @Test
    void testConvertCurrency_invalidAmount_throwsCurrenciesExeption() {
        CurrenciesRequestPostDto request1 = new CurrenciesRequestPostDto();
        request1.setFromCurrency("USD");
        request1.setToCurrency("RUB");
        request1.setAmount(0.0);

        CurrenciesRequestPostDto request2 = new CurrenciesRequestPostDto();
        request2.setFromCurrency("USD");
        request2.setToCurrency("RUB");
        request2.setAmount(-100.0);

        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request1));
        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request2));
    }

    @Test
    void testConvertCurrency_invalidCurrency_throwsCurrenciesExeption() {
        CurrenciesRequestPostDto request = new CurrenciesRequestPostDto();
        request.setFromCurrency("INVALID");
        request.setToCurrency("RUB");
        request.setAmount(100.0);

        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request));
    }

    @Test
    void testConvertCurrency_currencyNotFound_throwsCurrenciesExeption() {
        CurrenciesRequestPostDto request = new CurrenciesRequestPostDto();
        request.setFromCurrency("EUR");
        request.setToCurrency("RUB");
        request.setAmount(100.0);

        Map<String, Valute> mockValutes = new HashMap<>();
        when(currencyRateCache.loadValutesFromCbr()).thenReturn(mockValutes);

        assertThrows(CurrenciesExeption.class, () -> currenciesService.convertCurrency(request));
    }

}