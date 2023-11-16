package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.AttendanceDetails;
import com.attendance_management_system.model.Employee;
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
import java.util.Set;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceDetailsService attendanceDetailsService;

    /**
     * @RequestBody Location
     * @param email
     * @return
     */
    @PostMapping("/check-in/{email:.+}")
    public ResponseEntity<String> checkIn(@PathVariable String email,@RequestParam String location) throws RuntimeException {
        try {
            attendanceDetailsService.checkIn(email, location);

            return ResponseEntity.ok("Check-in successful");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     *
     * @param email
     * @param location
     * @return
     */
    @PostMapping("/check-out/{email:.+}")
    public ResponseEntity<String> checkOut(@PathVariable String email,@RequestParam String location) {
        try {
            attendanceDetailsService.checkOut(email, location);
            return ResponseEntity.ok("Check-out successful");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/fetch-start-time/{email:.+}")
    public ResponseEntity<LocalDateTime> fetchStartTime(@PathVariable String email) {
        try {
            LocalDateTime startTime = attendanceDetailsService.fetchStartTime(email);
            return ResponseEntity.ok(startTime);
        } catch (CustomException e) {
            return (ResponseEntity<LocalDateTime>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{email:.+}")
    public ResponseEntity getAttendanceDetailsForEmployee(@PathVariable String email,
               @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try{
            Map<LocalDate,List<AttendanceDetails>> attendanceDetails = attendanceDetailsService.getAttendanceDetailsForEmployee(email, startDate, endDate);
            return ResponseEntity.ok(attendanceDetails);
        }catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/employees/all")
    @ResponseBody
    public ResponseEntity getAttendanceDetailsForDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try{
            Map<LocalDate,List<AttendanceDetails>> attendanceDetails = attendanceDetailsService.getAttendanceDetailsForDateRange(startDate, endDate);
            return ResponseEntity.ok(attendanceDetails);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }
}
