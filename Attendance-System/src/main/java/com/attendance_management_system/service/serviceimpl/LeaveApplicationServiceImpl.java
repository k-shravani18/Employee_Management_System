package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.constants.AttendanceStatus;
import com.attendance_management_system.constants.LeaveStatus;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.exceptions.InsufficientLeavesException;
import com.attendance_management_system.exceptions.LeaveApplicationNotFoundException;
import com.attendance_management_system.exceptions.ResourceNotFoundException;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import com.attendance_management_system.model.LeavePolicy;
import com.attendance_management_system.model.payload.EmployeeLeave;
import com.attendance_management_system.repository.*;
import com.attendance_management_system.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeavePolicyRepository leavePolicyRepository;

    private final EmployeeRepository employeeRepository;

    private final AttendanceDetailsRepository attendanceDetailsRepository;
    private final AttendanceRepository attendanceRepository;

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

    /**
     * Creates a new leave application for the specified employee.
     * @param email            The email address of the employee applying for leave.
     * @param leaveApplication The leave application to be created.
     * @return The created leave application.
     * @throws CustomException If there is an issue creating the leave application
       or if there are insufficient leaves.
     */
    @Override
    public LeaveApplication createLeaveApplication(
            String email, LeaveApplication leaveApplication) throws CustomException {
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
                    leaveApplication.setStatus(LeaveStatus.PENDING.getValue());
                    return leaveApplicationRepository.save(leaveApplication);
                } else {
                    throw new InsufficientLeavesException("Insufficient leaves");
                }
            }
            else {
                throw  new ResourceNotFoundException("LeavePolicy not found");
            }

        } catch (DataAccessException | ResourceNotFoundException | InsufficientLeavesException e) {
            throw new CustomException(
                    "Failed to create leave application."+" " +
                            "Insufficient leaves. Available: " + availableLeaves
                    + ", Requested: " + noOfDays);
        }
    }

    /**
     * Calculates the total number of leaves taken by an employee based on the leave policy.
     * @param employee    The employee for whom leaves are calculated.
     * @param leavePolicy The leave policy for which leaves are calculated.
     * @return The total number of leaves taken.
     */
    private int getTotalNumberOfLeavesTaken(Employee employee, LeavePolicy leavePolicy) {
        List<LeaveApplication> leaveApplications = leaveApplicationRepository
                .findByEmployeeAndLeavePolicy(employee, leavePolicy)
                .stream()
                .filter(leave -> !leave.getStatus().equals(LeaveStatus.DENIED.getValue()))
                .toList();

        return leaveApplications.stream()
                .mapToInt(LeaveApplication::getNoOfDays)
                .sum();
    }

    /**
     * Retrieves a list of leaves by category for the specified employee.
     * @param employee The employee for whom leaves are fetched.
     * @return The list of leaves by category.
     */
    @Override
    public List<EmployeeLeave> getLeavesByCategory(Employee employee) {
        return leavePolicyRepository.findAll()
                .stream()
                .map(leavePolicy -> {
                    int leavesTaken = getTotalNumberOfLeavesTaken(employee, leavePolicy);
                    return new EmployeeLeave(leavePolicy.getLeaveType(),
                            leavePolicy.getMaximumLeaves() - leavesTaken, leavesTaken);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a leave application by its ID.
     * @param leaveApplicationId The ID of the leave application.
     * @return The leave application with the specified ID.
     * @throws CustomException If there is an issue fetching the leave application.
     */
    @Override
    public LeaveApplication getLeaveApplicationById(Long leaveApplicationId) throws CustomException {
        try {
            return leaveApplicationRepository.findById(leaveApplicationId)
                    .orElseThrow(() -> new EntityNotFoundException("LeaveApplication not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leaveApplication.", e);
        }
    }

    /**
     * Retrieves a list of all leave applications.
     * @return The list of all leave applications.
     * @throws CustomException If there is an issue fetching the leave applications.
     */
    @Override
    public List<LeaveApplication> getAllLeaveApplications() throws CustomException {
        try {
            return leaveApplicationRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leave applications.", e);
        }
    }

    /**
     * Retrieves a list of leave applications for the manager with the specified email.
     * @param email The email address of the manager.
     * @return The list of leave applications for the specified manager.
     * @throws CustomException If there is an issue fetching the leave applications.
     */
    @Override
    public List<LeaveApplication> getAllLeaveApplicationsByManager(String email) throws CustomException {
        try {
            Employee manager = employeeRepository.findByEmailId(email);
            return leaveApplicationRepository.findAll()
                    .stream()
                    .filter(leaveApplication ->
                            leaveApplication.getEmployee().getReportingManager()
                                    .equalsIgnoreCase(
                                            manager.getFirstName() + " " + manager.getLastName()))
                    .toList();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leave applications.", e);
        }
    }

    @Override
    public List<LeaveApplication> getLeaveApplicationsOfEmployee(String email) throws CustomException {
        try {
            Employee employee = employeeRepository.findByEmailId(email);
            return leaveApplicationRepository.findAll()
                    .stream()
                    .filter(leaveApplication ->
                            leaveApplication.getEmployee().equals(employee))
                    .toList();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch leave applications.", e);
        }
    }

    /**
     * Updates the status of a leave application based on the specified status.
     * @param leaveApplicationId The leave applicationId to be updated.
     * @param status           The status to be set (approved or denied).
     * @return The updated leave application.
     * @throws CustomException If there is an issue updating the leave application.
     */
    @Override
    public LeaveApplication updateLeaveApplication(Long leaveApplicationId, String status) throws CustomException {
        try {
            Optional<LeaveApplication> leaveApplicationOptional =
                    leaveApplicationRepository.findById(leaveApplicationId);

            if (leaveApplicationOptional.isPresent()) {
                LeaveApplication leaveApplication = leaveApplicationOptional.get();

                if (status.equalsIgnoreCase("approved")) {
                    leaveApplication.setStatus(LeaveStatus.APPROVED.getValue());
                } else {
                    LocalDate startDate = leaveApplication.getFromDate();
                    LocalDate endDate = leaveApplication.getToDate();
                    startDate.datesUntil(endDate).forEach(date -> {

                        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
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
            } else {
                throw new LeaveApplicationNotFoundException("Leave Application Not found for this Id");
            }
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update leave application.", e);
        } catch (LeaveApplicationNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes a leave application with the specified ID.
     * @param leaveApplicationId The ID of the leave application to be deleted.
     * @throws CustomException If there is an issue deleting the leave application.
     */
    @Override
    public void deleteLeaveApplication(Long leaveApplicationId) throws CustomException {
        try {
            leaveApplicationRepository.deleteById(leaveApplicationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete leave application.", e);
        }
    }

    /**
     * Calculates the number of days between two dates inclusively.
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return The number of days between the start and end dates inclusive.
     */
    public int calculateNumberOfDaysInclusive(LocalDate startDate, LocalDate endDate) {
        return (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }
}

