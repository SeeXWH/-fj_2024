package org.example.lesson_5_spring.repository;

import org.example.lesson_5_spring.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationRepositoryTest {

    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationRepository = new LocationRepository();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        List<Location> result = locationRepository.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnListOfLocations_whenRepositoryIsNotEmpty() {
        Location location1 = createTestLocation("location1");
        Location location2 = createTestLocation("location2");
        locationRepository.save(location1);
        locationRepository.save(location2);

        List<Location> result = locationRepository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(location1));
        assertTrue(result.contains(location2));
    }

    @Test
    void findById_shouldReturnNull_whenLocationNotFound() {
        Location result = locationRepository.findById("nonexistentId");
        assertNull(result);
    }

    @Test
    void findById_shouldReturnLocation_whenLocationExists() {
        Location location = createTestLocation("location1");
        locationRepository.save(location);

        Location result = locationRepository.findById("location1");

        assertEquals(location, result);
    }

    @Test
    void save_shouldAddLocationToRepository() {
        Location location = createTestLocation("location1");
        locationRepository.save(location);

        assertTrue(locationRepository.existsById("location1"));
        assertEquals(location, locationRepository.findById("location1"));
    }

    @Test
    void deleteById_shouldRemoveLocationFromRepository() {
        Location location = createTestLocation("location1");
        locationRepository.save(location);
        locationRepository.deleteById("location1");

        assertFalse(locationRepository.existsById("location1"));
    }

    @Test
    void existsById_shouldReturnFalse_whenLocationNotFound() {
        assertFalse(locationRepository.existsById("nonexistentId"));
    }

    @Test
    void existsById_shouldReturnTrue_whenLocationExists() {
        Location location = createTestLocation("location1");
        locationRepository.save(location);

        assertTrue(locationRepository.existsById("location1"));
    }

    private Location createTestLocation(String slug) {
        Location location = new Location();
        location.setSlug(slug);
        return location;
    }
}