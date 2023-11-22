package com.attendance_management_system.controller;

import com.attendance_management_system.model.BranchLocation;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.BranchLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class BranchLocationController {

    @Autowired
    private BranchLocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<BranchLocation> createBranchLocation(@RequestBody BranchLocation location) throws CustomException {
        BranchLocation createdBranchLocation = locationService.createBranchLocation(location);
        return new ResponseEntity<>(createdBranchLocation, HttpStatus.CREATED);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<BranchLocation> getBranchLocation(@PathVariable Long locationId) throws CustomException {
        BranchLocation location = locationService.getBranchLocationById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/getBranchLocations")
    public ResponseEntity<List<BranchLocation>> getAllBranchLocations() throws CustomException {
        List<BranchLocation> locations = locationService.getAllBranchLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PutMapping("/update/{locationId}")
    public ResponseEntity<BranchLocation> updateBranchLocation(@PathVariable Long locationId, @RequestBody BranchLocation location) throws CustomException {
        BranchLocation updatedBranchLocation = locationService.updateBranchLocation(locationId, location);
        return new ResponseEntity<>(updatedBranchLocation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<Void> deleteBranchLocation(@PathVariable Long locationId) throws CustomException {
        locationService.deleteBranchLocation(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
