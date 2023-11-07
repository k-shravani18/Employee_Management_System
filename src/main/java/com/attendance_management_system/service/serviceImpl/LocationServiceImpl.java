package com.attendance_management_system.service.serviceImpl;

import com.attendance_management_system.model.Location;
import com.attendance_management_system.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location updatedLocation) {
        Optional<Location> existingLocationOptional = locationRepository.findById(id);

        if (existingLocationOptional.isPresent()) {
            Location existingLocation = existingLocationOptional.get();
            existingLocation.setLocationName(updatedLocation.getLocationName());
            existingLocation.setLocationDetails(updatedLocation.getLocationDetails());
            return locationRepository.save(existingLocation);
        } else {
            // Handle case when location with given id is not found
            return null;
        }
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
