package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Attendance;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.AttendanceDetailsRepository;
import com.attendance_management_system.repository.AttendanceRepository;
import com.attendance_management_system.repository.EmployeeRepository;
import com.attendance_management_system.service.EmailService;
import com.attendance_management_system.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceDetailsRepository attendanceDetailsRepository;

    @Autowired
    private EmailService emailService;
    @Override
    @Scheduled(cron = "0 01 00 * * MON-FRI")
    public void createAttendanceLog() {

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendanceRepository.save(attendance);
    }

    @Override
    @Scheduled(cron = "0 50,20,50 9,10 * * MON-FRI")
    public void morningScheduledMethod() throws CustomException {
        try {
            List<Employee> allEmployees = employeeRepository.findAll();

                for (Employee employee : allEmployees) {
                    if (getAttendanceDetails(employee)== null) {
                        emailService.sendReminderEmailMorning(employee);
                    }
                }
        }catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    @Override
    @Scheduled(cron = "0 20,50 18,19 * * MON-FRI")
    public void eveningScheduledMethod() throws CustomException {
        try {
            List<Employee> allEmployees = employeeRepository.findAll();

                for (Employee employee : allEmployees) {
                    AttendanceDetails attendanceDetails=getAttendanceDetails(employee);
                    if (attendanceDetails!=null && attendanceDetails.getCheckOutTime()==null) {
                        emailService.sendReminderEmailEvening(employee);
                    }
                }
        }catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    @Override
    @Scheduled(cron = "0 59 23 * * MON-FRI")
    public void autoCheckOut() throws CustomException {
        try {
            List<Employee> allEmployees = employeeRepository.findAll();

            for (Employee employee : allEmployees) {

                AttendanceDetails attendanceDetails = getAttendanceDetails(employee);
                if(attendanceDetails!=null && attendanceDetails.getCheckOutTime() == null){
                    attendanceDetails.setCheckOutTime(LocalDateTime.now());
                    attendanceDetails.setCheckOutLocation("Nil");
                    attendanceDetails.setTotalTime(510);
                    attendanceDetailsRepository.save(attendanceDetails);
                }
            }
        }catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }

    }

    private AttendanceDetails getAttendanceDetails(Employee employee){
        Attendance attendance = attendanceRepository.findByDate(LocalDate.now());
        AttendanceDetails attendanceDetails = attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance);
        return attendanceDetails;
    }
}
