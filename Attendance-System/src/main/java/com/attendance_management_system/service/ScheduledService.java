package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface ScheduledService {
    void createAttendanceLog();

    void morningRemainder() throws CustomException;

    void eveningRemainder() throws CustomException;

    void autoCheckOut() throws CustomException;

    void markAbsentIfNotPresent() throws CustomException;


}
