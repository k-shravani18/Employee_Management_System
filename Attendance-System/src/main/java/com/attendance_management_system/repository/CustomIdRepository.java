package com.attendance_management_system.repository;

import com.attendance_management_system.model.id.CustomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomIdRepository extends JpaRepository<CustomId, String> {
}
