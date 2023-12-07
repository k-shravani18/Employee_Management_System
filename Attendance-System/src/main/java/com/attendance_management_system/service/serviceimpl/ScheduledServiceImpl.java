package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.constants.AttendanceStatus;
import com.attendance_management_system.constants.DayType;
import com.attendance_management_system.constants.HolidayType;
import com.attendance_management_system.constants.LeaveStatus;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.Attendance;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
import com.attendance_management_system.model.LeaveApplication;
import com.attendance_management_system.repository.*;
import com.attendance_management_system.service.EmailService;
import com.attendance_management_system.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceDetailsRepository attendanceDetailsRepository;
    private final HolidayCalenderRepository calenderRepository;
    private final LeaveApplicationRepository leaveApplicationRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    public ScheduledServiceImpl(EmployeeRepository employeeRepository,
                                AttendanceRepository attendanceRepository,
                                AttendanceDetailsRepository attendanceDetailsRepository,
                                HolidayCalenderRepository calenderRepository,
                                LeaveApplicationRepository leaveApplicationRepository) {

        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.attendanceDetailsRepository = attendanceDetailsRepository;
        this.calenderRepository = calenderRepository;
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    /**
     * Scheduled method to create an attendance log based on the current date,
     * marking it as a working day, weekend, or holiday.
     */
    @Override
    @Scheduled(cron = "0 30 10 * * ?")
    public void createAttendanceLog() {
        Attendance attendance = new Attendance();
        LocalDate date = LocalDate.now();
        DayOfWeek day = date.getDayOfWeek();

        attendance.setDate(date);
        if (checkHolidays()) {
            attendance.setDayType(DayType.HOLIDAY);
        } else if (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)) {
            attendance.setDayType(DayType.WEEKEND);
        } else {
            attendance.setDayType(DayType.WORKING_DAY);
        }
        attendanceRepository.save(attendance);
    }


    /**
     * Scheduled method to create employee attendance entries based on the current date.
     * @throws CustomException If there is an issue fetching data or creating attendance entries.
     */
    @Override
    @Scheduled(cron = "0 31 10 * * MON-FRI")
    public void createEmployeeAttendance() throws CustomException {
        try {
            if (!checkHolidays()) {
                employeeRepository.findAll().forEach(employee -> {
                    AttendanceDetails attendance = new AttendanceDetails();
                    attendance.setAttendance(attendanceRepository.findByDate(LocalDate.now()));
                    attendance.setEmployee(employee);

                    if (checkLeave(employee)) {
                        attendance.setStatus(AttendanceStatus.LEAVE);
                    } else {
                        attendance.setStatus(AttendanceStatus.OUT);
                    }

                    attendanceDetailsRepository.save(attendance);
                });
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Scheduled method to mark leave for employees based on leave applications.
     * @throws CustomException If there is an issue fetching data or marking leaves.
     */
    @Override
    @Scheduled(cron = "0 00 10 * * MON-FRI")
    public void markLeaveIfLeaveTaken() throws CustomException {
        try {
            if (!checkHolidays()) {
                getListOfAttendance().forEach(attendanceDetails -> {
                    if (checkLeave(attendanceDetails.getEmployee())) {
                        attendanceDetails.setStatus(AttendanceStatus.LEAVE);
                        attendanceDetailsRepository.save(attendanceDetails);
                    }
                });
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Scheduled method to mark employees as absent if they haven't checked in.
     * @throws CustomException If there is an issue fetching data or marking absences.
     */
    @Override
    @Scheduled(cron = "0 30 18 * * MON-FRI")
    public void markAbsentIfNotPresent() throws CustomException {
        try {
            if (!checkHolidays()) {
                getListOfAttendance().forEach(attendanceDetails -> {
                    attendanceDetails.setStatus(AttendanceStatus.ABSENT);
                    attendanceDetailsRepository.save(attendanceDetails);
                });
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Scheduled method to automatically check out employees at the end of the day.
     * @throws CustomException If there is an issue fetching data or checking out employees.
     */
    @Override
    @Scheduled(cron = "0 55 23 * * MON-FRI")
    public void autoCheckOut() throws CustomException {
        try {
            if (!checkHolidays()) {
                employeeRepository.findAll().forEach(employee -> {
                    AttendanceDetails attendanceDetails = getAttendanceDetails(employee);
                    if (attendanceDetails.getCheckOutTime() == null) {
                        attendanceDetails.setCheckOutTime(LocalDateTime.now());
                        attendanceDetails.setCheckOutLocation("Nil");
                        attendanceDetails.setTotalTime(510);
                        attendanceDetailsRepository.save(attendanceDetails);
                    }
                });
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Scheduled method to send morning reminders to employees who need to check in.
     * @throws CustomException If there is an issue fetching data or sending reminders.
     */
    @Override
    @Scheduled(cron = "0 50,20,50 9,10 * * MON-FRI")
    public void morningRemainder() throws CustomException {
        try {
            if (!checkHolidays()) {
                employeeRepository.findAll()
                        .stream()
                        .filter(this::employeeStatusCheck)
                        .forEach(employee -> emailService.sendReminderEmailMorning(employee));
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Scheduled method to send evening reminders to employees who haven't checked out yet.
     * @throws CustomException If there is an issue fetching data or sending reminders.
     */
    @Override
    @Scheduled(cron = "0 20,50 18,19 * * MON-FRI")
    public void eveningRemainder() throws CustomException {
        try {
            if (!checkHolidays()) {
                employeeRepository.findAll().forEach(employee -> {
                    AttendanceDetails attendanceDetails = getAttendanceDetails(employee);

                    if (attendanceDetails.getCheckOutTime() == null &&
                            attendanceDetails.getStatus().equals(AttendanceStatus.PRESENT)) {
                        emailService.sendReminderEmailEvening(employee);
                    }
                });
            }
        } catch (DataAccessException e) {
            throw new CustomException("Unable to fetch data", e);
        }
    }

    /**
     * Helper method to get attendance details for a specific employee.
     * @param employee The employee for whom attendance details are needed.
     * @return Attendance details for the specified employee.
     */
    private AttendanceDetails getAttendanceDetails(Employee employee) {
        Attendance attendance = attendanceRepository.findByDate(LocalDate.now());
        return attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance);
    }

    /**
     * Helper method to get a list of attendance details for employees who haven't checked out.
     * @return List of attendance details for employees who haven't checked out.
     */
    private List<AttendanceDetails> getListOfAttendance() {
        return attendanceDetailsRepository.findAll()
                .stream()
                .filter(attendance -> (attendance.getAttendance().getDate().equals(LocalDate.now()))
                        && attendance.getStatus().equals(AttendanceStatus.OUT))
                .collect(Collectors.toList());
    }

    /**
     * Helper method to check if today is a holiday.
     * @return True if today is a holiday, false otherwise.
     */
    private boolean checkHolidays() {
        return calenderRepository.existsByHolidayDateAndHolidayType(LocalDate.now(), HolidayType.MANDATORY);
    }

    /**
     * Helper method to check if an employee has taken leave for today.
     * @param employee The employee for whom leave status needs to be checked.
     * @return True if the employee has taken leave, false otherwise.
     */
    private boolean checkLeave(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        LeaveApplication leaveApplication = leaveApplicationRepository
                .findByEmployeeAndFromDateLessThanEqualAndToDateGreaterThanEqual(employee, currentDate, currentDate);
        return leaveApplication != null && !leaveApplication.getStatus().equals(LeaveStatus.DENIED.getValue());
    }

    /**
     * Helper method to check the attendance status of an employee for today.
     * @param employee The employee for whom attendance status needs to be checked.
     * @return True if the employee has checked in, false otherwise.
     */
    private boolean employeeStatusCheck(Employee employee) {
        return attendanceDetailsRepository.findByEmployeeAndAttendance(
                employee, attendanceRepository.findByDate(LocalDate.now()))
                .getStatus().equals(AttendanceStatus.OUT);
    }
}