package org.example.lesson_5_spring.service;

import lombok.AllArgsConstructor;
import org.example.lesson_5_spring.exeption.TExeption;
import org.example.lesson_5_spring.model.Location;
import org.example.lesson_5_spring.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location getLocationById(String id) {
        if (locationRepository.existsById(id)) {
            return locationRepository.findById(id);
        } else {
            throw new TExeption("Location not found");
        }
    }

    public Location addLocation(Location location) {
        if (locationRepository.existsById(location.getSlug())) {
            throw new TExeption("Location already exists");
        }
        return locationRepository.save(location);
    }

    public Location updateLocation(Location location) {
        if (locationRepository.existsById(location.getSlug())) {
            return locationRepository.save(location);
        } else {
            throw new TExeption("Location not found");
        }
    }

    public void deleteLocation(String id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new TExeption("Location not found");
        }
    }
}

