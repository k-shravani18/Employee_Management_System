package com.attendance_management_system.repository;

import com.attendance_management_system.model.BranchLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchLocationRepository extends JpaRepository<BranchLocation, Long> {
    BranchLocation findByLocationName(String locationName);
}
