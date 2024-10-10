package org.example.lesson_5_spring.controller;

import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.exeption.CurrenciesExeption;
import org.example.lesson_5_spring.exeption.TExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@LogExecutionTime
@ControllerAdvice
public class ExeptionController {
    @ExceptionHandler(TExeption.class)
    public ResponseEntity<String> handleException(TExeption ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        if (ex.getMessage().contains("Categories not found")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Categories already exists")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Location not found")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Location already exists")) {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(message);
    }
    @ExceptionHandler(CurrenciesExeption.class)
    public ResponseEntity<String> handleException(CurrenciesExeption ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        if (ex.getMessage().contains("Currencies not found")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("incorrect currency")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("not all parameters were passed")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("amount must be greater than 0")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Error loading currency rates")) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }
        return ResponseEntity.status(status).body(message);
    }
}
