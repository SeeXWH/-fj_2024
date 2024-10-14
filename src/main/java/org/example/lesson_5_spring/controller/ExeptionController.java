package org.example.lesson_5_spring.controller;

import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.exception.TException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@LogExecutionTime
@ControllerAdvice
public class ExeptionController {
    @ExceptionHandler(TException.class)
    public ResponseEntity<String> handleException(TException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        if (ex.getMessage().contains("Categories not found")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Categories already exists")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Location not found")) {
            status = HttpStatus.BAD_REQUEST;
        }else if (ex.getMessage().contains("Location already exists")) {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(message);
    }
}
