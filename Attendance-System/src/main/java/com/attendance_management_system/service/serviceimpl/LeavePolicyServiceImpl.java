package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.repository.LeavePolicyRepository;
import com.attendance_management_system.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService {

    private final LeavePolicyRepository leavePolicyRepository;

    @Autowired
    public LeavePolicyServiceImpl(LeavePolicyRepository leavePolicyRepository) {
        this.leavePolicyRepository = leavePolicyRepository;
    }

    @Override
    public LeavePolicy createLeavePolicy(LeavePolicy leavePolicy) throws CustomException {
        try {
            return leavePolicyRepository.save(leavePolicy);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create leavePolicy.", e);
        }
    }

    @Override
    public LeavePolicy getLeavePolicyById(Long leavePolicyId) throws CustomException {
        try {
            return leavePolicyRepository.findById(leavePolicyId)
                    .orElseThrow(() -> new EntityNotFoundException("LeavePolicy not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leavePolicy.", e);
        }
    }

    @Override
    public List<LeavePolicy> getAllLeavePolicies() throws CustomException {
        try {
            return leavePolicyRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leavePolicy.", e);
        }
    }

    @Override
    public LeavePolicy updateLeavePolicy(Long leavePolicyId, LeavePolicy leavePolicy) throws CustomException {
        try {
            leavePolicy.setLeavePolicyId(leavePolicyId);
            return leavePolicyRepository.save(leavePolicy);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update leavePolicy.", e);
        }
    }

    @Override
    public void deleteLeavePolicy(Long leavePolicyId) throws CustomException {
        try {
            leavePolicyRepository.deleteById(leavePolicyId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete leavePolicy.", e);
        }
    }
}
