package com.attendance_management_system.repository;

import com.attendance_management_system.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    boolean existsByDesignationName(String designationName);
}
