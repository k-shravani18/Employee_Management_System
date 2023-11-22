package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ScheduledService {
    void createAttendanceLog();
    void morningRemainder() throws CustomException;
    void eveningRemainder() throws CustomException;
    void autoCheckOut() throws CustomException;
    void markAbsentIfNotPresent() throws CustomException;
    void createEmployeeAttendance() throws CustomException;
    void markLeaveIfLeaveTaken() throws CustomException;
}
