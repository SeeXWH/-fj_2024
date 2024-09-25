package org.example.lesson_5_spring.components;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.model.Location;
import org.example.lesson_5_spring.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class KudagoDataLocationInitializer implements CommandLineRunner {

    private RestTemplate restTemplate = new RestTemplate();
    private final LocationRepository locationRepository;

    public KudagoDataLocationInitializer(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    @LogExecutionTime
    public void run(String... args) throws Exception {
        log.info("Kudago Data Location initialized");
        String apiUrl = "https://kudago.com/public-api/v1.4/locations";
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(apiUrl, String.class);
        if (response != null && !response.isEmpty()) {
            try {
                Location[] locations = restTemplate.getForObject(apiUrl, Location[].class);
                for (Location location : locations) {
                    locationRepository.getLocationsMap().put(location.getSlug(), location);
                    log.info("Location: {}", location.getSlug() + " has been uploaded successfully.");
                }
                log.info("Location data has been uploaded successfully.");
            } catch (Exception e) {
                log.error("Error during Kudago data initialization: {}", e.getMessage(), e);
            }
        } else{
            log.error("An empty response was received from the API.");
        }

    }
}
