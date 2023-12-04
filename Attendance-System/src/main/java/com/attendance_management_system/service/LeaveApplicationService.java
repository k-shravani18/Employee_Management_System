package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveApplicationService {
    LeaveApplication createLeaveApplication(String email, LeaveApplication leaveApplication) throws CustomException;
    List getLeavesByCategory(Employee employee);
    LeaveApplication getLeaveApplicationById(Long leaveApplicationId) throws CustomException;
    List<LeaveApplication> getAllLeaveApplications() throws CustomException;
    LeaveApplication updateLeaveApplication(Long leaveApplicationId, String status) throws CustomException;
    void deleteLeaveApplication(Long leaveApplicationId) throws CustomException;

    List<LeaveApplication> getAllLeaveApplicationsByManager(String email) throws CustomException;
}
