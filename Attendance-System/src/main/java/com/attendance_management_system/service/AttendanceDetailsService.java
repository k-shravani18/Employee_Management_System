package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.payload.AttendanceTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public interface AttendanceDetailsService {
    void checkIn(String email, String location) throws CustomException;
    void checkOut(String email, String location) throws CustomException;
    AttendanceTime fetchStartTime(String email) throws CustomException;
    Map getAttendanceDetailsForEmployee(
            String email, LocalDate startDate, LocalDate endDate) throws CustomException;
    Map getAttendanceDetailsForDateRange(LocalDate startDate, LocalDate endDate) throws CustomException;
    Map getAttendanceDetailsForDateRangeAndLocation(
            String location, LocalDate startDate, LocalDate endDate) throws CustomException;

}
