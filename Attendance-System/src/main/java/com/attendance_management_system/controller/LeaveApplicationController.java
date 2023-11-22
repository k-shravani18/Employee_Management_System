package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import com.attendance_management_system.model.payload.EmployeeLeave;
import com.attendance_management_system.repository.EmployeeRepository;
import com.attendance_management_system.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaveApplications")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/add/{email:.+}")
    public ResponseEntity createLeaveApplication(@PathVariable String email, @RequestBody LeaveApplication leaveApplication) throws CustomException, RuntimeException{
        LeaveApplication createdLeaveApplication;
        try {
            createdLeaveApplication = leaveApplicationService.createLeaveApplication(email, leaveApplication);
            return new ResponseEntity<>(createdLeaveApplication, HttpStatus.CREATED);
        }catch (CustomException e){
            return ResponseEntity.ok(e.getMessage());

        }
    }

    @PostMapping("/update")
    public ResponseEntity updateLeaveApplication(@RequestBody LeaveApplication leaveApplication, String status) throws CustomException, RuntimeException{
        LeaveApplication updatedLeaveApplication;
        try {
            updatedLeaveApplication = leaveApplicationService.updateLeaveApplication(leaveApplication, status);
            return new ResponseEntity<>(updatedLeaveApplication, HttpStatus.CREATED);
        }catch (CustomException e){
            return ResponseEntity.ok(e.getMessage());

        }
    }

    @GetMapping("/fetch/{email:.+}")
    public ResponseEntity<List<EmployeeLeave>> getLeavesByCategory(@PathVariable String  email) throws CustomException {
        Employee employee = employeeRepository.findByEmailId(email);
        List<EmployeeLeave> leave = leaveApplicationService.getLeavesByCategory(employee);
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }

    @GetMapping("/getLeaveApplications")
    public ResponseEntity<List<LeaveApplication>> getAllLeaveApplications() throws CustomException {
        List<LeaveApplication> leaveApplications = leaveApplicationService.getAllLeaveApplications();
        return new ResponseEntity<>(leaveApplications, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{leaveApplicationId}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long leaveApplicationId) throws CustomException {
        leaveApplicationService.deleteLeaveApplication(leaveApplicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
