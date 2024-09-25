package org.example.lesson_5_spring.repository;

import lombok.Data;
import org.example.lesson_5_spring.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Data
public class LocationRepository {
    private HashMap<String, Location> locationsMap = new HashMap<>();

    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        locationsMap.forEach((k, v) -> locations.add(v));
        return locations;
    }

    public Location findById(String id) {
        return locationsMap.get(id);
    }

    public Location save(Location location) {
        locationsMap.put(location.getSlug(), location);
        return location;
    }


    public void deleteById(String id) {
        locationsMap.remove(id);
    }

    public boolean existsById(String id) {
        return locationsMap.containsKey(id);
    }
}
