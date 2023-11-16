package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.constants.AttendanceStatus;
import com.attendance_management_system.model.Attendance;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.exceptions.AlreadyCheckedInException;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.AttendanceDetailsRepository;
import com.attendance_management_system.repository.AttendanceRepository;
import com.attendance_management_system.repository.EmployeeRepository;
import com.attendance_management_system.service.AttendanceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class AttendanceDetailsServiceImpl implements AttendanceDetailsService {

    @Autowired
    private AttendanceDetailsRepository attendanceDetailsRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private  EmployeeRepository employeeRepository;



    @Override
    public void checkIn(String email, String location) throws CustomException {
        try {

            Employee employee = employeeRepository.findByEmailId(email);
            Attendance attendance = attendanceRepository.findByDate(LocalDate.now());
            if(attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance) == null) {

                AttendanceDetails attendanceDetails = new AttendanceDetails();
                attendanceDetails.setEmployee(employee);
                attendanceDetails.setCheckInLocation(location);
                attendanceDetails.setCheckInTime(LocalDateTime.now());
                attendanceDetails.setAttendance(attendance);
                attendanceDetails.setStatus(AttendanceStatus.PRESENT);
                attendanceDetailsRepository.save(attendanceDetails);
            }
            else{
                throw new AlreadyCheckedInException("You have already checked in today");
            }
        } catch (DataAccessException e) {
            throw new CustomException("Failed to perform check-in.", e);
        } catch (AlreadyCheckedInException e) {
            throw new RuntimeException("Oops! Your one time check in process for today was completed", e);
        }
    }

    @Override
    public void checkOut(String email, String location) throws CustomException {

        try {
            Employee employee = employeeRepository.findByEmailId(email);

            Attendance attendance = attendanceRepository.findByDate(LocalDate.now());

            AttendanceDetails attendanceDetails = attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance);
            attendanceDetails.setCheckOutLocation(location);
            attendanceDetails.setCheckOutTime(LocalDateTime.now());

            Duration timeDifference = calculateTimeDifference(attendanceDetails.getCheckInTime(), LocalDateTime.now());
            long totalTime = timeDifference.toMinutes();
            attendanceDetails.setTotalTime(totalTime);

            attendanceDetailsRepository.save(attendanceDetails);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to perform check-out.", e);
        }
    }

    @Override
    public LocalDateTime fetchStartTime(String email) throws CustomException {
        try {
        Employee employee = employeeRepository.findByEmailId(email);

        Attendance attendance = attendanceRepository.findByDate(LocalDate.now());
        AttendanceDetails attendanceDetails = attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance);

            if( attendanceDetails != null && attendanceDetails.getCheckOutTime() == null) {

               return attendanceDetails.getCheckInTime();
            }
            else if( attendanceDetails != null && attendanceDetails.getCheckOutTime() != null) {

                Duration duration = calculateTimeDifference(attendanceDetails.getCheckInTime(), attendanceDetails.getCheckOutTime());
                String formattedTime = formatDuration(duration);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime time = LocalTime.parse(formattedTime, formatter);
                LocalDate today = LocalDate.now();

                return LocalDateTime.of(today,time);
            }
            else{
                LocalDate today = LocalDate.now();
                LocalTime midnight = LocalTime.MIDNIGHT;
                return LocalDateTime.of(today, midnight);
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    public Duration calculateTimeDifference(LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        return Duration.between(checkInTime, checkOutTime);
    }

    public String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

}
