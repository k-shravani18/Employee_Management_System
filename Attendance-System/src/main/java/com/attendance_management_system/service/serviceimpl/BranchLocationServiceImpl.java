package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.BranchLocation;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.BranchLocationRepository;
import com.attendance_management_system.service.BranchLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BranchLocationServiceImpl implements BranchLocationService {


    private final BranchLocationRepository locationRepository;

    @Autowired
    public BranchLocationServiceImpl(BranchLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Creates a new branch location.
     * @param location The branch location to be created.
     * @return The created branch location.
     * @throws CustomException If there is an issue creating the location.
     * @author Kamil Praseej
     */
    @Override
    public BranchLocation createBranchLocation(BranchLocation location) throws CustomException {
        try {
            return locationRepository.save(location);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create location.", e);
        }
    }

    /**
     * Retrieves a branch location by its ID.
     * @param locationId The ID of the branch location to be retrieved.
     * @return The branch location with the specified ID.
     * @throws CustomException If the branch location with the given ID
       is not found or if there is an issue fetching the location.
     * @author Kamil Praseej
     */
    @Override
    public BranchLocation getBranchLocationById(Long locationId) throws CustomException {
        try {
            return locationRepository.findById(locationId)
                    .orElseThrow(() -> new EntityNotFoundException("BranchLocation not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch location.", e);
        }
    }

    /**
     * Retrieves all branch locations.
     * @return A list of all branch locations.
     * @throws CustomException If there is an issue fetching the locations.
     * @author Kamil Praseej
     */
    @Override
    public List<BranchLocation> getAllBranchLocations() throws CustomException {
        try {
            return locationRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch location.", e);
        }
    }

    /**
     * Updates an existing branch location.
     * @param locationId The ID of the branch location to be updated.
     * @param location   The updated branch location information.
     * @return The updated branch location.
     * @throws CustomException If there is an issue updating the location.
     * @author Kamil Praseej
     */
    @Override
    public BranchLocation updateBranchLocation(Long locationId, BranchLocation location) throws CustomException {
        try {
            location.setLocationId(locationId);
            return locationRepository.save(location);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update location.", e);
        }
    }

    /**
     * Deletes a branch location by its ID.
     * @param locationId The ID of the branch location to be deleted.
     * @throws CustomException If there is an issue deleting the location.
     * @author Kamil Praseej
     */
    @Override
    public void deleteBranchLocation(Long locationId) throws CustomException {
        try {
            locationRepository.deleteById(locationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete location.", e);
        }
    }

}
