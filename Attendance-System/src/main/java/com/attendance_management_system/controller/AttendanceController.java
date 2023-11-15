package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.AttendanceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
}
