package com.attendance_management_system.controller;

import com.attendance_management_system.model.LeaveApp;
import com.attendance_management_system.repository.LeaveAppRepository;
import com.attendance_management_system.service.serviceImpl.LeaveAppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaveApp")
public class LeaveAppController {
    @Autowired
    private LeaveAppServiceImpl leaveAppService;

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveApp>> getAllLeaveApp() {
        List<LeaveApp> leaveApps = leaveAppService.getAllLeaveApp();
        return ResponseEntity.ok(leaveApps);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<LeaveApp> getById(@PathVariable Long id) {
        Optional<LeaveApp> leaveApp = leaveAppService.getById(id);
        return leaveApp.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createLeaveApp")
    public ResponseEntity<LeaveApp> createLeaveApp(@RequestBody LeaveApp leaveApp) {
        LeaveApp createdLeaveApp = leaveAppService.createLeaveApp(leaveApp);
        return ResponseEntity.ok(createdLeaveApp);
    }

    @PutMapping("/updatePolicy/{id}")
    public ResponseEntity<LeaveApp> updateLeaveApp(@PathVariable Long id, @RequestBody LeaveApp updateLeaveApp) {
        LeaveApp updatedLeaveApp = leaveAppService.updateLeaveApp(id, updateLeaveApp);
        return ResponseEntity.ok(updatedLeaveApp);
    }

    @DeleteMapping("/deletePolicy/{id}")
    public ResponseEntity<Void> deleteLeaveApp(@PathVariable Long id) {
        leaveAppService.deleteLeaveApp(id);
        return ResponseEntity.noContent().build();
    }
}
