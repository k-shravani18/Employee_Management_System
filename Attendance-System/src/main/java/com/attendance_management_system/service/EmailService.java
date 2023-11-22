package com.attendance_management_system.service;

import com.attendance_management_system.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendReminderEmailMorning(Employee employee);
    void sendReminderEmailEvening(Employee employee);
}
