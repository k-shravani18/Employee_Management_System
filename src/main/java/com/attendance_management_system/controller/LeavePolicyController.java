package com.attendance_management_system.controller;

import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.repository.LeavePolicyRepository;
import com.attendance_management_system.service.serviceImpl.LeavePolicyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/policy")
public class LeavePolicyController {
    @Autowired
    private LeavePolicyServiceImpl leavePolicyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<LeavePolicy>> getAll(){
        List<LeavePolicy>policies=leavePolicyService.getAll();
        return ResponseEntity.ok(policies);
    }
    @GetMapping("/getByLeavePolicyId/{id}")
    public ResponseEntity<LeavePolicy> getById(@PathVariable Long id){
        Optional<LeavePolicy> leavePolicy= leavePolicyService.getById(id);
        return leavePolicy.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/createPolicy")
    public ResponseEntity<LeavePolicy> createLeavePolicy(@RequestBody LeavePolicy leavePolicy){
        LeavePolicy policy= leavePolicyService.createLeavePolicy(leavePolicy);
        return ResponseEntity.ok(policy);
    }
    @PutMapping("/updatePolicy/{id}")
    public ResponseEntity<LeavePolicy> updateLeavePolicy(@PathVariable Long id,@RequestBody LeavePolicy updateLeavePolicy){
       LeavePolicy leavePolicy= leavePolicyService.updateLeavePolicy(id,updateLeavePolicy);
       return ResponseEntity.ok(leavePolicy);
    }
    @GetMapping("/deletePolicy/{id}")
    public ResponseEntity<Void> deleteLeavePolicy(@PathVariable Long id){
        leavePolicyService.deleteLeavePolicy(id);
        return ResponseEntity.noContent().build();
    }

}
