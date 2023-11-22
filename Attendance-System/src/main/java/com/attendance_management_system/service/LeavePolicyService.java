package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.LeavePolicy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeavePolicyService {
    LeavePolicy createLeavePolicy(LeavePolicy leavePolicy) throws CustomException;
    LeavePolicy getLeavePolicyById(Long leavePolicyId) throws CustomException;
    List<LeavePolicy> getAllLeavePolicies() throws CustomException;
    LeavePolicy updateLeavePolicy(Long leavePolicyId, LeavePolicy leavePolicy) throws CustomException;
    void deleteLeavePolicy(Long leavePolicyId) throws CustomException;
}
