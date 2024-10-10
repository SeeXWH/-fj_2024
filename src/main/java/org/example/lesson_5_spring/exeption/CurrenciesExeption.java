package org.example.lesson_5_spring.exeption;

public class CurrenciesExeption extends RuntimeException {
    public CurrenciesExeption(String message) {
        super(message);
    }
}
