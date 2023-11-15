package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface ScheduledService {
    void createAttendanceLog();

    void morningScheduledMethod() throws CustomException;

    void eveningScheduledMethod() throws CustomException;

    void autoCheckOut() throws CustomException;



}
