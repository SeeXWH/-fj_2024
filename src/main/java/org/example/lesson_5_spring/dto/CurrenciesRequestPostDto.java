package org.example.lesson_5_spring.dto;

import lombok.Data;

@Data
public class CurrenciesRequestPostDto {
    private String fromCurrency;
    private String toCurrency;
    private double amount;
}
