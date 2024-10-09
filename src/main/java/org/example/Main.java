package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
@OpenAPIDefinition
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        ObjectMapper mapper = new ObjectMapper();
        String[] jsonFiles = {"city.json", "city-error.json"};

        for (String jsonFile : jsonFiles) {
            try {
                JsonNode rootNode = mapper.readTree(new File(jsonFile));
                if (rootNode.has("coords") && rootNode.has("slug")) {
                    String jsonString = rootNode.toString();
                    System.out.println(jsonString);
                    City city = mapper.readValue(jsonString, City.class);
                    logger.info("Successfully parsed city from {}: {}", jsonFile, city);

                    try {
                        String xml = city.toXML();
                        Files.writeString(Path.of("city.xml"), xml, StandardCharsets.UTF_8);
                        logger.info("XML saved to file: city.xml");
                    } catch (Exception e) {
                        logger.error("Error converting city to XML or saving to file: {}", e.getMessage(), e);
                    }
                } else {
                    logger.warn("Invalid JSON structure in file: {}. Missing 'coords' field.", jsonFile);
                }
            } catch (IOException e) {
                logger.error("Error parsing JSON file: {} - {}", jsonFile, e.getMessage(), e);
            }
        }

        System.out.println();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}