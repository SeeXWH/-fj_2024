package org.example.lesson_5_spring.components;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.model.Categories;
import org.example.lesson_5_spring.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class KudagoDataCategoriesInitializer implements CommandLineRunner {
    @Autowired
    private RestTemplate restTemplate;
    private final CategoriesRepository categoriesRepository;

    public KudagoDataCategoriesInitializer(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    @LogExecutionTime
    public void run(String... args) throws Exception {
        log.info("Kudago Data categories initialization");
        String apiUrl = "https://kudago.com/public-api/v1.4/place-categories";
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(apiUrl, String.class);
        if (response != null && !response.isEmpty()) {
            try {
                Categories[] categories = restTemplate.getForObject(apiUrl, Categories[].class);
                for (Categories category : categories) {
                    categoriesRepository.getCategoriesMap().put(category.getId(), category);
                    log.info("Category: " + category.getName() + " has been uploaded successfully.");
                }
                log.info("Category data has been uploaded successfully.");
            } catch (Exception e) {
                log.error("Error during Kudago data initialization: {}", e.getMessage(), e);
            }
        } else {
            log.error("An empty response was received from the API.");
        }
    }
}
