package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.constants.AttendanceStatus;
import com.attendance_management_system.constants.LeaveStatus;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.exceptions.InsufficientLeavesException;
import com.attendance_management_system.exceptions.ResourceNotFoundException;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.model.payload.EmployeeLeave;
import com.attendance_management_system.repository.*;
import com.attendance_management_system.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    private LeaveApplicationRepository leaveApplicationRepository;

    private LeavePolicyRepository leavePolicyRepository;

    private EmployeeRepository employeeRepository;

    private AttendanceDetailsRepository attendanceDetailsRepository;
    private AttendanceRepository attendanceRepository;

    @Autowired
    public LeaveApplicationServiceImpl(LeaveApplicationRepository leaveApplicationRepository,
                                       LeavePolicyRepository leavePolicyRepository,
                                       EmployeeRepository employeeRepository,
                                       AttendanceDetailsRepository attendanceDetailsRepository,
                                       AttendanceRepository attendanceRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leavePolicyRepository = leavePolicyRepository;
        this.employeeRepository = employeeRepository;
        this.attendanceDetailsRepository = attendanceDetailsRepository;
        this.attendanceRepository=attendanceRepository;
    }

    @Override
    public LeaveApplication createLeaveApplication(String email, LeaveApplication leaveApplication) throws CustomException {
        int leavesTaken, noOfDays=0, maximumLeaves, availableLeaves=0;

        try {
            Employee employee = employeeRepository.findByEmailId(email);
            leaveApplication.setEmployee(employee);
             noOfDays = calculateNumberOfDaysInclusive(leaveApplication.getFromDate(), leaveApplication.getToDate());

            Optional<LeavePolicy> leavePolicyOptional =
                    leavePolicyRepository.findById(leaveApplication.getLeavePolicy().getLeavePolicyId());

            if (leavePolicyOptional.isPresent()) {
                LeavePolicy leavePolicy = leavePolicyOptional.get();
                maximumLeaves = leavePolicy.getMaximumLeaves();

                leavesTaken = getTotalNumberOfLeavesTaken(employee, leavePolicy);
                availableLeaves = maximumLeaves - leavesTaken;

                if (availableLeaves >= noOfDays) {
                    leaveApplication.setNoOfDays(noOfDays);
                    return leaveApplicationRepository.save(leaveApplication);
                } else {
                    throw new InsufficientLeavesException("Insufficient leaves");
                }
            }
            else {
                throw  new ResourceNotFoundException("LeavePolicy not found");
            }

        } catch (DataAccessException | ResourceNotFoundException | InsufficientLeavesException e) {
            throw new CustomException("Failed to create leave application."+" Insufficient leaves. Available: " + availableLeaves
                    + ", Requested: " + noOfDays);
        }
    }

    private int getTotalNumberOfLeavesTaken(Employee employee, LeavePolicy leavePolicy) {

        List<LeaveApplication> leaveApplications = leaveApplicationRepository
                .findByEmployeeAndLeavePolicy(employee, leavePolicy)
                .stream()
                .filter(leave -> !leave.getStatus().equals(LeaveStatus.DENIED.getValue()))
                .collect(Collectors.toList());

        int totalLeaves = leaveApplications.stream()
                .mapToInt(LeaveApplication::getNoOfDays)
                .sum();

        return totalLeaves;
    }


    @Override
    public List getLeavesByCategory(Employee employee){

        List<EmployeeLeave> employeeLeaves= leavePolicyRepository.findAll()
                .stream()
                .map(leavePolicy -> {
                    int leavesTaken = getTotalNumberOfLeavesTaken(employee, leavePolicy);
                    return new EmployeeLeave(leavePolicy.getLeaveType(),
                            leavePolicy.getMaximumLeaves()-leavesTaken, leavesTaken);
                })
                .collect(Collectors.toList());
        return employeeLeaves;
    }

    @Override
    public LeaveApplication getLeaveApplicationById(Long leaveApplicationId) throws CustomException {
        try {
            return leaveApplicationRepository.findById(leaveApplicationId)
                    .orElseThrow(() -> new EntityNotFoundException("LeaveApplication not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leaveApplication.", e);
        }
    }

    @Override
    public List<LeaveApplication> getAllLeaveApplications() throws CustomException {
        try {
            return leaveApplicationRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leaveApplication.", e);
        }
    }

    @Override
    public LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication, String status) throws CustomException {
        try {
            if(status.equalsIgnoreCase("approved")){
                leaveApplication.setStatus(LeaveStatus.APPROVED.getValue());
            }
            else {
                LocalDate startDate = leaveApplication.getFromDate();
                LocalDate endDate = leaveApplication.getToDate();
                startDate.datesUntil(endDate).forEach(date -> {
                    if(date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                        AttendanceDetails attendanceDetails =
                                attendanceDetailsRepository.findByEmployeeAndAttendance
                                        (leaveApplication.getEmployee(), attendanceRepository.findByDate(date));
                        attendanceDetails.setStatus(AttendanceStatus.ABSENT);
                        attendanceDetailsRepository.save(attendanceDetails);
                    }
                });
                leaveApplication.setStatus(LeaveStatus.DENIED.getValue());
            }
            return leaveApplicationRepository.save(leaveApplication);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update leaveApplication.", e);
        }
    }

    @Override
    public void deleteLeaveApplication(Long leaveApplicationId) throws CustomException {
        try {
            leaveApplicationRepository.deleteById(leaveApplicationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete leaveApplication.", e);
        }
    }

    public int calculateNumberOfDaysInclusive(LocalDate startDate, LocalDate endDate) {
        return (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }
}
