package org.example.lesson_5_spring.service;

import org.example.lesson_5_spring.model.Location;
import org.example.lesson_5_spring.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void findAll_shouldReturnListOfLocations() {
        List<Location> locations = Arrays.asList(new Location("test", "test"), new Location("test2", "test2"));
        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> result = locationService.findAll();

        assertEquals(locations, result);
    }

    @Test
    void getLocationById_shouldReturnLocation_whenLocationExists() {
        String id = "testId";
        Location location = new Location();
        location.setSlug(id);
        when(locationRepository.existsById(id)).thenReturn(true);
        when(locationRepository.findById(id)).thenReturn(location);

        Location result = locationService.getLocationById(id);

        assertEquals(location, result);
    }

    @Test
    void getLocationById_shouldThrowRuntimeException_whenLocationNotFound() {
        String id = "testId";
        when(locationRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> locationService.getLocationById(id));
    }

    @Test
    void addLocation_shouldSaveLocation_whenLocationDoesNotExist() {
        Location location = new Location();
        location.setSlug("testSlug");
        when(locationRepository.existsById(location.getSlug())).thenReturn(false);
        when(locationRepository.save(location)).thenReturn(location);

        Location result = locationService.addLocation(location);

        assertEquals(location, result);
    }

    @Test
    void addLocation_shouldThrowRuntimeException_whenLocationAlreadyExists() {
        Location location = new Location();
        location.setSlug("testSlug");
        when(locationRepository.existsById(location.getSlug())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> locationService.addLocation(location));
    }

    @Test
    void updateLocation_shouldSaveLocation_whenLocationExists() {
        Location location = new Location();
        location.setSlug("testSlug");
        when(locationRepository.existsById(location.getSlug())).thenReturn(true);
        when(locationRepository.save(location)).thenReturn(location);

        Location result = locationService.updateLocation(location);

        assertEquals(location, result);
    }

    @Test
    void updateLocation_shouldThrowRuntimeException_whenLocationNotFound() {
        Location location = new Location();
        location.setSlug("testSlug");
        when(locationRepository.existsById(location.getSlug())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> locationService.updateLocation(location));
    }

    @Test
    void deleteLocation_shouldDeleteLocation_whenLocationExists() {
        String id = "testId";
        when(locationRepository.existsById(id)).thenReturn(true);

        locationService.deleteLocation(id);

        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteLocation_shouldThrowRuntimeException_whenLocationNotFound() {
        String id = "testId";
        when(locationRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> locationService.deleteLocation(id));
    }
}