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

    @Override
    public BranchLocation createBranchLocation(BranchLocation location) throws CustomException {
        try {
            return locationRepository.save(location);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create location.", e);
        }
    }

    @Override
    public BranchLocation getBranchLocationById(Long locationId) throws CustomException {
        try {
            return locationRepository.findById(locationId)
                    .orElseThrow(() -> new EntityNotFoundException("BranchLocation not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch location.", e);
        }
    }

    @Override
    public List<BranchLocation> getAllBranchLocations() throws CustomException {
        try {
            return locationRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch location.", e);
        }
    }

    @Override
    public BranchLocation updateBranchLocation(Long locationId, BranchLocation location) throws CustomException {
        try {
            location.setLocationId(locationId);
            return locationRepository.save(location);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update location.", e);
        }
    }

    @Override
    public void deleteBranchLocation(Long locationId) throws CustomException {
        try {
            locationRepository.deleteById(locationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete location.", e);
        }
    }
}
