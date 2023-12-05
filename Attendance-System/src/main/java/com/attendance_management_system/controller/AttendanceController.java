package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.payload.AttendanceTime;
import com.attendance_management_system.service.AttendanceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    @Autowired
    private AttendanceDetailsService attendanceDetailsService;

    /**
     * Check-in endpoint for marking attendance at a specific location
     * @param email    Employee email
     * @param location Check-in location
     * @return ResponseEntity with check-in status
     * @throws RuntimeException in case of check-in failure
     */
    @PostMapping("/check-in/{email:.+}")
    public ResponseEntity<String> checkIn(
            @PathVariable String email, @RequestParam String location) throws RuntimeException {
        try {
            attendanceDetailsService.checkIn(email, location);
            return ResponseEntity.ok("Check-in successful");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * Check-out endpoint for marking the end of attendance at a specific location
     * @param email    Employee email
     * @param location Check-out location
     * @return ResponseEntity with check-out status
     */
    @PostMapping("/check-out/{email:.+}")
    public ResponseEntity<String> checkOut(@PathVariable String email, @RequestParam String location) {
        try {
            attendanceDetailsService.checkOut(email, location);
            return ResponseEntity.ok("Check-out successful");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to fetch the start time of attendance for a given email
     * @param email Employee email
     * @return ResponseEntity with the start time
     */
    @GetMapping("/fetch-start-time/{email:.+}")
    public ResponseEntity<AttendanceTime> fetchStartTime(@PathVariable String email) {
        try {
            AttendanceTime attendanceTime = attendanceDetailsService.fetchStartTime(email);
            return ResponseEntity.ok(attendanceTime);
        } catch (CustomException e) {
            return (ResponseEntity<AttendanceTime>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get attendance details for an employee within a date range
     * @param email     Employee email
     * @param startDate Start date of the date range
     * @param endDate   End date of the date range
     * @return ResponseEntity with attendance details for the employee
     */
    @GetMapping("/employee/{email:.+}")
    public ResponseEntity getAttendanceDetailsForEmployee(
            @PathVariable String email,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Map<LocalDate, List<AttendanceDetails>> attendanceDetails =
                    attendanceDetailsService.getAttendanceDetailsForEmployee(email, startDate, endDate);
            return ResponseEntity.ok(attendanceDetails);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Endpoint to get attendance details for all employees within a date range
     * @param startDate Start date of the date range
     * @param endDate   End date of the date range
     * @return ResponseEntity with attendance details for all employees
     */
    @GetMapping("/employees/all")
    @ResponseBody
    public ResponseEntity getAttendanceDetailsForDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Map<LocalDate, List<AttendanceDetails>> attendanceDetails =
                    attendanceDetailsService.getAttendanceDetailsForDateRange(startDate, endDate);
            return ResponseEntity.ok(attendanceDetails);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Endpoint to get attendance details for all employees within a date range and a specific office location
     * @param location  Office location
     * @param startDate Start date of the date range
     * @param endDate   End date of the date range
     * @return ResponseEntity with attendance details for all employees in the given location and date range
     */
    @GetMapping("/employees/location")
    @ResponseBody
    public ResponseEntity getAttendanceDetailsForDateRangeAndLocation(
            @RequestParam String location,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Map<LocalDate, List<AttendanceDetails>> attendanceDetails =
                    attendanceDetailsService.
                            getAttendanceDetailsForDateRangeAndLocation(location, startDate, endDate);
            return ResponseEntity.ok(attendanceDetails);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }
}
