package org.example.lesson_5_spring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurrenciesRequestPostDto {
    private String fromCurrency;
    private String toCurrency;
    private double amount;
}
