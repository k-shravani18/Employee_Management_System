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
@CrossOrigin("*")
public class BranchLocationController {

    @Autowired
    private BranchLocationService locationService;

    /**
     * Creates a new branch location.
     * @param location The branch location information to be added.
     * @return The created branch location.
     * @throws CustomException If there is an issue creating the branch location.
     */
    @PostMapping("/add")
    public ResponseEntity<BranchLocation> createBranchLocation(
            @RequestBody BranchLocation location) throws CustomException {
        BranchLocation createdBranchLocation = locationService.createBranchLocation(location);
        return new ResponseEntity<>(createdBranchLocation, HttpStatus.CREATED);
    }

    /**
     * Retrieves a branch location by its ID.
     * @param locationId The ID of the branch location to be retrieved.
     * @return The branch location with the specified ID.
     * @throws CustomException If the branch location with the given ID
       is not found or if there is an issue fetching the branch location.
     */
    @GetMapping("/{locationId}")
    public ResponseEntity<BranchLocation> getBranchLocation(@PathVariable Long locationId) throws CustomException {
        BranchLocation location = locationService.getBranchLocationById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * Retrieves all branch locations.
     * @return List of all branch locations.
     * @throws CustomException If there is an issue fetching the branch locations.
     */
    @GetMapping("/getBranchLocations")
    public ResponseEntity<List<BranchLocation>> getAllBranchLocations() throws CustomException {
        List<BranchLocation> locations = locationService.getAllBranchLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    /**
     * Updates an existing branch location.
     * @param locationId The ID of the branch location to be updated.
     * @param location   The updated branch location information.
     * @return The updated branch location.
     * @throws CustomException If there is an issue updating the branch location.
     */
    @PutMapping("/update/{locationId}")
    public ResponseEntity<BranchLocation> updateBranchLocation(
            @PathVariable Long locationId, @RequestBody BranchLocation location) throws CustomException {
        BranchLocation updatedBranchLocation = locationService.updateBranchLocation(locationId, location);
        return new ResponseEntity<>(updatedBranchLocation, HttpStatus.OK);
    }

    /**
     * Deletes a branch location by its ID.
     * @param locationId The ID of the branch location to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the branch location.
     */
    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<Void> deleteBranchLocation(@PathVariable Long locationId) throws CustomException {
        locationService.deleteBranchLocation(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
