package org.example.lesson_5_spring.components;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.example.lesson_5_spring.exeption.CurrenciesExeption;
import org.example.lesson_5_spring.model.ValCurs;
import org.example.lesson_5_spring.model.Valute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CurrencyRateCache {

    @Value("${central-bank.api.url}")
    private String CBR_API_URL;

    @Autowired
    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public CurrencyRateCache(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Cacheable(value = "valutes")
    public Map<String, Valute> loadValutesFromCbr() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cbrCircuitBreaker");

        return circuitBreaker.run(() -> {
            try {
                URL url = new URL(CBR_API_URL);
                InputStream xmlInput = url.openStream();

                JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(xmlInput);
                List<Valute> valuteList = valCurs.getValutes();
                System.out.println(valuteList);
                Map<String, Valute> valuteMap = new HashMap<>();
                for (Valute valute : valuteList) {
                    valuteMap.put(valute.getCharCode(), valute);
                }
                System.out.println(valuteMap);
                return valuteMap;
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при получении курсов валют", e);
            }
        }, throwable -> {
            log.error("Error loading currency rates", throwable);
            throw new CurrenciesExeption("Error loading currency rates");
        });
    }

    @CacheEvict(value = "valutes", allEntries = true)
    @Scheduled(fixedRate = 3600000)
    public void emptyCache() {
        log.error("Clearing cache");
    }
}