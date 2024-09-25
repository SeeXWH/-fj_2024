package org.example.lesson_5_spring.controller;

import lombok.AllArgsConstructor;
import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.model.Location;
import org.example.lesson_5_spring.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@LogExecutionTime
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;
    @GetMapping("/all")
    public List<Location> findAll() {
        return locationService.findAll();
    }
    @GetMapping("/byId/{id}")
    public Location findById(@PathVariable("id") String id) {
        return locationService.getLocationById(id);
    }
    @PostMapping("/add")
    public Location save(@RequestBody Location location) {
        return locationService.addLocation(location);
    }
    @PutMapping("/update")
    public Location update(@RequestBody Location location) {
        return locationService.updateLocation(location);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        locationService.deleteLocation(id);
    }
}
