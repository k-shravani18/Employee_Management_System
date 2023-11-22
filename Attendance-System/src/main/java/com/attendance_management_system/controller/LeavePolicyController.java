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

    @PostMapping("/add")
    public ResponseEntity<LeavePolicy> createLeavePolicy(@RequestBody LeavePolicy leavePolicy) throws CustomException {
        LeavePolicy createdLeavePolicy = leavePolicyService.createLeavePolicy(leavePolicy);
        return new ResponseEntity<>(createdLeavePolicy, HttpStatus.CREATED);
    }

    @GetMapping("/{leavePolicyId}")
    public ResponseEntity<LeavePolicy> getLeavePolicy(@PathVariable Long leavePolicyId) throws CustomException {
        LeavePolicy leavePolicy = leavePolicyService.getLeavePolicyById(leavePolicyId);
        return new ResponseEntity<>(leavePolicy, HttpStatus.OK);
    }

    @GetMapping("/getLeavePolicies")
    public ResponseEntity<List<LeavePolicy>> getAllLeavePolicies() throws CustomException {
        List<LeavePolicy> leavePolicys = leavePolicyService.getAllLeavePolicies();
        return new ResponseEntity<>(leavePolicys, HttpStatus.OK);
    }

    @PutMapping("/update/{leavePolicyId}")
    public ResponseEntity<LeavePolicy> updateLeavePolicy(@PathVariable Long leavePolicyId, @RequestBody LeavePolicy leavePolicy) throws CustomException {
        LeavePolicy updatedLeavePolicy = leavePolicyService.updateLeavePolicy(leavePolicyId, leavePolicy);
        return new ResponseEntity<>(updatedLeavePolicy, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{leavePolicyId}")
    public ResponseEntity<Void> deleteLeavePolicy(@PathVariable Long leavePolicyId) throws CustomException {
        leavePolicyService.deleteLeavePolicy(leavePolicyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
