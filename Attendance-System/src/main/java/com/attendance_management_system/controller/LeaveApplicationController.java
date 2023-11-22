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

    /**
     * Endpoint to create a leave application for a specific employee
     * @param email             Employee email
     * @param leaveApplication  Leave application data
     * @return ResponseEntity with the created leave application
     * @throws RuntimeException if there is an issue creating the leave application
     * @author Kamil Praseej
     */
    @PostMapping("/add/{email:.+}")
    public ResponseEntity createLeaveApplication(
            @PathVariable String email, @RequestBody LeaveApplication leaveApplication) throws RuntimeException {
        LeaveApplication createdLeaveApplication;
        try {
            createdLeaveApplication = leaveApplicationService.createLeaveApplication(email, leaveApplication);
            return new ResponseEntity<>(createdLeaveApplication, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Endpoint to update the status of a leave application
     * @param leaveApplication Leave application data
     * @param status           New status of the leave application
     * @return ResponseEntity with the updated leave application
     * @throws RuntimeException if there is an issue updating the leave application
     * @author Kamil Praseej
     */
    @PostMapping("/update")
    public ResponseEntity updateLeaveApplication(
            @RequestBody LeaveApplication leaveApplication, String status) throws RuntimeException {
        LeaveApplication updatedLeaveApplication;
        try {
            updatedLeaveApplication = leaveApplicationService.updateLeaveApplication(leaveApplication, status);
            return new ResponseEntity<>(updatedLeaveApplication, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Endpoint to fetch leaves by category for a specific employee
     * @param email Employee email
     * @return ResponseEntity with a list of leaves for the employee
     * @throws CustomException if there is an issue fetching leaves by category
     * @author Kamil Praseej
     */
    @GetMapping("/fetch/{email:.+}")
    public ResponseEntity<List<EmployeeLeave>> getLeavesByCategory(@PathVariable String email) {
        Employee employee = employeeRepository.findByEmailId(email);
        List leave = leaveApplicationService.getLeavesByCategory(employee);
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }

    /**
     * Endpoint to get details of all leave applications
     * @return ResponseEntity with a list of all leave applications
     * @throws CustomException if there is an issue fetching leave applications details
     * @author Kamil Praseej
     */
    @GetMapping("/getLeaveApplications")
    public ResponseEntity<List<LeaveApplication>> getAllLeaveApplications() throws CustomException {
        List<LeaveApplication> leaveApplications = leaveApplicationService.getAllLeaveApplications();
        return new ResponseEntity<>(leaveApplications, HttpStatus.OK);
    }

    /**
     * Endpoint to get details of all leave applications for a specific manager
     * @param email Manager email
     * @return ResponseEntity with a list of leave applications for the manager
     * @throws CustomException if there is an issue fetching leave applications for the manager
     * @author Kamil Praseej
     */
    @GetMapping("/getLeaveApplications/{email:.+}")
    public ResponseEntity<List<LeaveApplication>> getAllLeaveApplicationsForManager(
            @PathVariable String email) throws CustomException {
        List<LeaveApplication> leaveApplications =
                leaveApplicationService.getAllLeaveApplicationsByManager(email);
        return new ResponseEntity<>(leaveApplications, HttpStatus.OK);
    }

    /**
     * Endpoint to delete a leave application by leaveApplicationId
     * @param leaveApplicationId Leave application ID to be deleted
     * @return ResponseEntity indicating success (NO_CONTENT)
     * @throws CustomException if there is an issue deleting the leave application
     * @author Kamil Praseej
     */
    @DeleteMapping("/delete/{leaveApplicationId}")
    public ResponseEntity<Void> deleteLeaveApplication(
            @PathVariable Long leaveApplicationId) throws CustomException {
        leaveApplicationService.deleteLeaveApplication(leaveApplicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

