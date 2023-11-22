package com.attendance_management_system.service;

import com.attendance_management_system.model.BranchLocation;
import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchLocationService {
    BranchLocation createBranchLocation(BranchLocation location) throws CustomException;
    BranchLocation getBranchLocationById(Long locationId) throws CustomException;

    List<BranchLocation> getAllBranchLocations() throws CustomException;
    BranchLocation updateBranchLocation(Long locationId, BranchLocation location) throws CustomException;
    void deleteBranchLocation(Long locationId) throws CustomException;
}
