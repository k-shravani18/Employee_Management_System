//package com.attendance_management_system.service.serviceimpl;
//
//import com.attendance_management_system.constants.AttendanceStatus;
//import com.attendance_management_system.constants.DayType;
//import com.attendance_management_system.constants.LeaveStatus;
//import com.attendance_management_system.exceptions.CustomException;
//import com.attendance_management_system.model.Attendance;
//import com.attendance_management_system.model.AttendanceDetails;
//import com.attendance_management_system.model.Employee;
//import com.attendance_management_system.model.LeaveApplication;
//import com.attendance_management_system.repository.*;
//import com.attendance_management_system.service.EmailService;
//import com.attendance_management_system.service.ScheduledService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ScheduledServiceImpl implements ScheduledService {
//
//    private final EmployeeRepository employeeRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final AttendanceDetailsRepository attendanceDetailsRepository;
//    private final HolidayCalenderRepository calenderRepository;
//    private final LeaveApplicationRepository leaveApplicationRepository;
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    public ScheduledServiceImpl(EmployeeRepository employeeRepository,
//                                AttendanceRepository attendanceRepository,
//                                AttendanceDetailsRepository attendanceDetailsRepository,
//                                HolidayCalenderRepository calenderRepository,
//                                LeaveApplicationRepository leaveApplicationRepository) {
//
//        this.employeeRepository = employeeRepository;
//        this.attendanceRepository = attendanceRepository;
//        this.attendanceDetailsRepository = attendanceDetailsRepository;
//        this.calenderRepository = calenderRepository;
//        this.leaveApplicationRepository = leaveApplicationRepository;
//    }
//    @Override
//    @Scheduled(cron = "0 36 10 * * ?")
//    public void createAttendanceLog() {
//
//        Attendance attendance = new Attendance();
//        LocalDate date = LocalDate.now();
//        DayOfWeek day = date.getDayOfWeek();
//
//        attendance.setDate(date);
//        if(checkHolidays()){
//            attendance.setDayType(DayType.HOLIDAY);
//        }
//        else if(day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)){
//            attendance.setDayType(DayType.WEEKEND);
//        }
//        else {
//            attendance.setDayType(DayType.WORKING_DAY);
//        }
//        attendanceRepository.save(attendance);
//    }
//
//    @Override
//    @Scheduled(cron = "0 50,20,50 9,10 * * MON-FRI")
//    public void morningRemainder() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                employeeRepository.findAll()
//                    .forEach(employee -> {
//                        if(employeeStatusCheck(employee)){
//                            emailService.sendReminderEmailMorning(employee);
//                        }
//                    });
//            }
//        }catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//    }
//
//    @Override
//
//    @Scheduled(cron = "0 20,50 18,19 * * MON-FRI")
//    public void eveningRemainder() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                employeeRepository.findAll()
//                    .forEach(employee -> {
//                        if (getAttendanceDetails(employee).getCheckOutTime() == null) {
//                            emailService.sendReminderEmailEvening(employee);
//                        }
//                    });
//            }
//        }catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//    }
//
//    @Override
//    @Scheduled(cron = "0 37 10 * * MON-FRI")
//    public void createEmployeeAttendance() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                employeeRepository.findAll()
//                    .forEach(employee -> {
//
//                        AttendanceDetails attendance = new AttendanceDetails();
//                        attendance.setAttendance(attendanceRepository.findByDate(LocalDate.now()));
//                        attendance.setEmployee(employee);
//
//                        if (checkLeave(employee)) {
//                            attendance.setStatus(AttendanceStatus.LEAVE);
//                        } else {
//                            attendance.setStatus(AttendanceStatus.OUT);
//                        }
//
//                        attendanceDetailsRepository.save(attendance);
//                    });
//            }
//        }catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//    }
//
//    @Override
//    @Scheduled(cron = "0 00 10 * * MON-FRI")
//    public void markLeaveIfLeaveTaken() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                getListOfAttendance()
//                .forEach(attendanceDetails -> {
//                    if (checkLeave(attendanceDetails.getEmployee())) {
//                        attendanceDetails.setStatus(AttendanceStatus.LEAVE);
//                        attendanceDetailsRepository.save(attendanceDetails);
//                    }
//                });
//            }
//        }catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//    }
//
//    @Override
//    @Scheduled(cron = "0 30 18 * * MON-FRI")
//    public void markAbsentIfNotPresent() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                getListOfAttendance()
//                .forEach(attendanceDetails -> {
//                    attendanceDetails.setStatus(AttendanceStatus.ABSENT);
//                    attendanceDetailsRepository.save(attendanceDetails);
//                });
//            }
//        } catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//    }
//
//    @Override
//    @Scheduled(cron = "0 59 23 * * MON-FRI")
//    public void autoCheckOut() throws CustomException {
//        try {
//            if(!checkHolidays()) {
//                employeeRepository.findAll()
//                    .forEach(employee -> {
//                        AttendanceDetails attendanceDetails = getAttendanceDetails(employee);
//                        if (attendanceDetails.getCheckOutTime() == null) {
//                            attendanceDetails.setCheckOutTime(LocalDateTime.now());
//                            attendanceDetails.setCheckOutLocation("Nil");
//                            attendanceDetails.setTotalTime(510);
//                            attendanceDetailsRepository.save(attendanceDetails);
//                        }
//                    });
//            }
//        }catch (DataAccessException e) {
//            throw new CustomException("Unable to fetch data", e);
//        }
//
//    }
//
//    private AttendanceDetails getAttendanceDetails(Employee employee){
//        Attendance attendance = attendanceRepository.findByDate(LocalDate.now());
//        return attendanceDetailsRepository.findByEmployeeAndAttendance(employee, attendance);
//    }
//
//    private List<AttendanceDetails> getListOfAttendance(){
//       return attendanceDetailsRepository.findAll()
//                .stream()
//                .filter(attendance -> (attendance.getAttendance().getDate().equals(LocalDate.now()))
//                        && attendance.getStatus().equals(AttendanceStatus.OUT))
//               .collect(Collectors.toList());
//    }
//
//    private boolean checkHolidays(){
//        return calenderRepository.existsByHolidayDate(LocalDate.now());
//    }
//
//    private boolean checkLeave(Employee employee) {
//        LocalDate currentDate = LocalDate.now();
//        LeaveApplication leaveApplication = leaveApplicationRepository.findByEmployeeAndFromDateLessThanEqualAndToDateGreaterThanEqual(
//                employee, currentDate, currentDate);
//        return leaveApplication != null && !leaveApplication.getStatus().equals(LeaveStatus.DENIED.getValue());
//    }
//
//    private boolean employeeStatusCheck(Employee employee){
//       return attendanceDetailsRepository.findByEmployeeAndAttendance
//                (employee, attendanceRepository.findByDate(LocalDate.now()))
//               .getStatus().equals(AttendanceStatus.OUT);
//    }
//
//}
