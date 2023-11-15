package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface AttendanceDetailsService {
    void checkIn(String email, String location) throws CustomException;

    void checkOut(String email, String location) throws CustomException;

    LocalDateTime fetchStartTime(String email) throws CustomException;
}
