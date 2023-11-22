package com.attendance_management_system.repository;

import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import com.attendance_management_system.model.LeavePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByEmployeeAndLeavePolicy(Employee employee, LeavePolicy leavePolicy);
    LeaveApplication findByEmployeeAndFromDateLessThanEqualAndToDateGreaterThanEqual(
            Employee employee, LocalDate fromDate, LocalDate toDate );
}
