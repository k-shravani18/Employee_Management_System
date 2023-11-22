package com.attendance_management_system.repository;

import com.attendance_management_system.constants.AttendanceStatus;
import com.attendance_management_system.constants.LeaveStatus;
import com.attendance_management_system.model.Attendance;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, Long> {
    AttendanceDetails findByEmployeeAndAttendance(Employee employee, Attendance attendance);
    List<AttendanceDetails> findByAttendance(Attendance attendance);
    List<AttendanceDetails> findByEmployeeAndStatus(Employee employee, AttendanceStatus status);


}
