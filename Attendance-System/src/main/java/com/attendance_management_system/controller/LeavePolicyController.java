package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leavePolicies")
public class LeavePolicyController {

    @Autowired
    private LeavePolicyService leavePolicyService;

    /**
     * Creates a new leavePolicy.
     * @param leavePolicy The leavePolicy information to be added.
     * @return The created leavePolicy.
     * @throws CustomException If there is an issue creating the leavePolicy.
     * @author Kamil Praseej
     */
    @PostMapping("/add")
    public ResponseEntity<LeavePolicy> createLeavePolicy(@RequestBody LeavePolicy leavePolicy) throws CustomException {
        LeavePolicy createdLeavePolicy = leavePolicyService.createLeavePolicy(leavePolicy);
        return new ResponseEntity<>(createdLeavePolicy, HttpStatus.CREATED);
    }

    /**
     * Retrieves a leavePolicy by its ID.
     * @param leavePolicyId The ID of the leavePolicy to be retrieved.
     * @return The leavePolicy with the specified ID.
     * @throws CustomException If the leavePolicy with the given ID
    is not found or if there is an issue fetching the leavePolicy.
     * @author Kamil Praseej
     */
    @GetMapping("/{leavePolicyId}")
    public ResponseEntity<LeavePolicy> getLeavePolicy(@PathVariable Long leavePolicyId) throws CustomException {
        LeavePolicy leavePolicy = leavePolicyService.getLeavePolicyById(leavePolicyId);
        return new ResponseEntity<>(leavePolicy, HttpStatus.OK);
    }

    /**
     * Retrieves all leavePolicies.
     * @return List of all leavePolicies.
     * @throws CustomException If there is an issue fetching the leavePolicies.
     */
    @GetMapping("/getLeavePolicies")
    public ResponseEntity<List<LeavePolicy>> getAllLeavePolicies() throws CustomException {
        List<LeavePolicy> leavePolicies = leavePolicyService.getAllLeavePolicies();
        return new ResponseEntity<>(leavePolicies, HttpStatus.OK);
    }

    /**
     * Updates an existing leavePolicy.
     * @param leavePolicyId The ID of the leavePolicy to be updated.
     * @param leavePolicy   The updated leavePolicy information.
     * @return The updated leavePolicy.
     * @throws CustomException If there is an issue updating the leavePolicy.
     */
    @PutMapping("/update/{leavePolicyId}")
    public ResponseEntity<LeavePolicy> updateLeavePolicy(@PathVariable Long leavePolicyId, @RequestBody LeavePolicy leavePolicy) throws CustomException {
        LeavePolicy updatedLeavePolicy = leavePolicyService.updateLeavePolicy(leavePolicyId, leavePolicy);
        return new ResponseEntity<>(updatedLeavePolicy, HttpStatus.OK);
    }

    /**
     * Deletes a leavePolicy by its ID.
     * @param leavePolicyId The ID of the leavePolicy to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the leavePolicy.
     */
    @DeleteMapping("/delete/{leavePolicyId}")
    public ResponseEntity<Void> deleteLeavePolicy(@PathVariable Long leavePolicyId) throws CustomException {
        leavePolicyService.deleteLeavePolicy(leavePolicyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
