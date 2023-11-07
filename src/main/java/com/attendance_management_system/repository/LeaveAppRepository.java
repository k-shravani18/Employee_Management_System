package com.attendance_management_system.repository;

import com.attendance_management_system.model.LeaveApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveAppRepository extends JpaRepository<LeaveApp,Long> {
}
