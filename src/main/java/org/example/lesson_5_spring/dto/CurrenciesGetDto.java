package org.example.lesson_5_spring.dto;

import lombok.Data;

@Data
public class CurrenciesGetDto {
    private String currency;
    private Double rate;
}
