package org.example.lesson_5_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrenciesAnswerPostDto {
    private String fromCurrency;
    private String toCurrency;
    private double convertedAmount;
}
