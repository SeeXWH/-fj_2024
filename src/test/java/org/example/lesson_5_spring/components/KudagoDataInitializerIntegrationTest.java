package org.example.lesson_5_spring.components;

import org.example.lesson_5_spring.model.Categories;
import org.example.lesson_5_spring.model.Location;
import org.example.lesson_5_spring.repository.CategoriesRepository;
import org.example.lesson_5_spring.repository.LocationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.utility.DockerImageName;
import org.wiremock.integrations.testcontainers.WireMockContainer;


import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KudagoDataInitializerIntegrationTest {

    private WireMockContainer wireMockContainer;

    @Autowired
    private KudagoDataCategoriesInitializer categoriesInitializer;

    @Autowired
    private KudagoDataLocationInitializer locationInitializer;

    @MockBean
    private CategoriesRepository categoriesRepository;

    @MockBean
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        wireMockContainer = new WireMockContainer(DockerImageName.parse("wiremock/wiremock:latest"));
        wireMockContainer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockContainer.stop();
    }

    @Test
    void testRunCategories() throws Exception {
        Map<String, Categories> mockCategoriesMap = new HashMap<>();
        doReturn(mockCategoriesMap).when(categoriesRepository).getCategoriesMap();

        configureFor(wireMockContainer.getHost(), wireMockContainer.getPort());
        stubFor(get(urlEqualTo("/public-api/v1.4/place-categories"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("categories-response.json")));

        categoriesInitializer.run();

    }

    @Test
    void testRunLocations() throws Exception {
        Map<String, Location> mockLocationsMap = new HashMap<>();
        doReturn(mockLocationsMap).when(locationRepository).getLocationsMap();

        configureFor(wireMockContainer.getHost(), wireMockContainer.getPort());
        stubFor(get(urlEqualTo("/public-api/v1.4/locations"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("locations-response.json")));

        locationInitializer.run();

    }
}