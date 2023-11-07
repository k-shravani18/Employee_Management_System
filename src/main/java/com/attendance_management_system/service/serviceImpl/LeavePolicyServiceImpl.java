package com.attendance_management_system.service.serviceImpl;

import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.repository.LeavePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeavePolicyServiceImpl {
    @Autowired
    private LeavePolicyRepository leavePolicyRepository;

    public List<LeavePolicy> getAll(){
        return leavePolicyRepository.findAll();
    }
    public Optional<LeavePolicy>  getById(Long id){
        return leavePolicyRepository.findById(id);
    }
    public LeavePolicy createLeavePolicy(LeavePolicy leavePolicy){
        return leavePolicyRepository.save(leavePolicy);
    }
    public LeavePolicy updateLeavePolicy(Long id,LeavePolicy updateLeavePolicy){
        Optional<LeavePolicy> existPolicy=leavePolicyRepository.findById(id);
        if(existPolicy.isPresent()){
            LeavePolicy leavePolicy =existPolicy.get();
            leavePolicy.setLeaveType(updateLeavePolicy.getLeaveType());
            leavePolicy.setTotalLeaves(updateLeavePolicy.getTotalLeaves());
            leavePolicy.setLeavesPerMonth(updateLeavePolicy.getLeavesPerMonth());
            leavePolicy.setCarryForwardLeaves(updateLeavePolicy.getCarryForwardLeaves());
            leavePolicy.setLeaveApplications(updateLeavePolicy.getLeaveApplications());
            return leavePolicyRepository.save(updateLeavePolicy);
        }
        else {
            return null;
        }
    }
    public void deleteLeavePolicy(Long id){
        leavePolicyRepository.deleteById(id);
    }
}
