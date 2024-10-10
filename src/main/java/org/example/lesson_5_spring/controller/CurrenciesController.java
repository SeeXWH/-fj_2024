package org.example.lesson_5_spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lesson_5_spring.dto.CurrenciesAnswerPostDto;
import org.example.lesson_5_spring.dto.CurrenciesGetDto;
import org.example.lesson_5_spring.dto.CurrenciesRequestPostDto;
import org.example.lesson_5_spring.service.СurrenciesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {

    private final СurrenciesService service;

    public CurrenciesController(СurrenciesService service) {
        this.service = service;
    }

    @Operation(summary = "Получить курс валюты",
            description = "Возвращает курс заданной валюты по отношению к рублю.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CurrenciesGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректный код валюты",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена",
                    content = @Content)
    })
    @GetMapping("/rates/{code}")
    public ResponseEntity<CurrenciesGetDto> getCurrencies(@PathVariable("code") String code) {
        return ResponseEntity.ok(service.getCurrencies(code));
    }

    @Operation(summary = "Конвертировать валюту",
            description = "Конвертирует заданную сумму из одной валюты в другую.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная конвертация",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CurrenciesAnswerPostDto.class))}),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена",
                    content = @Content)
    })
    @PostMapping("/convert")
    public ResponseEntity<CurrenciesAnswerPostDto> convertCurrencies(
            @RequestBody CurrenciesRequestPostDto dto) {
        return ResponseEntity.ok(service.convertCurrency(dto));
    }
}