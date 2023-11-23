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

    /**
     * Creates a new leave policy.
     * @param leavePolicy The leave policy to be created.
     * @return The created leave policy.
     * @throws CustomException If there is an issue creating the leave policy.
     */
    @Override
    public LeavePolicy createLeavePolicy(LeavePolicy leavePolicy) throws CustomException {
        try {
            return leavePolicyRepository.save(leavePolicy);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create leave policy.", e);
        }
    }

    /**
     * Retrieves a leave policy by its ID.
     * @param leavePolicyId The ID of the leave policy.
     * @return The leave policy with the specified ID.
     * @throws CustomException If there is an issue fetching the leave policy.
     */
    @Override
    public LeavePolicy getLeavePolicyById(Long leavePolicyId) throws CustomException {
        try {
            return leavePolicyRepository.findById(leavePolicyId)
                    .orElseThrow(() -> new EntityNotFoundException("Leave policy not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leave policy.", e);
        }
    }

    /**
     * Retrieves a list of all leave policies.
     * @return The list of all leave policies.
     * @throws CustomException If there is an issue fetching the leave policies.
     */
    @Override
    public List<LeavePolicy> getAllLeavePolicies() throws CustomException {
        try {
            return leavePolicyRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leave policies.", e);
        }
    }

    /**
     * Updates an existing leave policy.
     * @param leavePolicyId The ID of the leave policy to be updated.
     * @param leavePolicy   The updated leave policy.
     * @return The updated leave policy.
     * @throws CustomException If there is an issue updating the leave policy.
     */
    @Override
    public LeavePolicy updateLeavePolicy(Long leavePolicyId, LeavePolicy leavePolicy) throws CustomException {
        try {
            leavePolicy.setLeavePolicyId(leavePolicyId);
            return leavePolicyRepository.save(leavePolicy);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update leave policy.", e);
        }
    }

    /**
     * Deletes a leave policy with the specified ID.
     * @param leavePolicyId The ID of the leave policy to be deleted.
     * @throws CustomException If there is an issue deleting the leave policy.
     */
    @Override
    public void deleteLeavePolicy(Long leavePolicyId) throws CustomException {
        try {
            leavePolicyRepository.deleteById(leavePolicyId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete leave policy.", e);
        }
    }

}
