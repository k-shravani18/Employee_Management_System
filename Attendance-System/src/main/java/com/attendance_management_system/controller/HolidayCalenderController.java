package com.attendance_management_system.controller;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.HolidayCalender;
import com.attendance_management_system.service.HolidayCalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayCalenderController {

    @Autowired
    private HolidayCalenderService holidayService;

    @PostMapping("/add")
    public ResponseEntity<HolidayCalender> createHolidayCalender(@RequestBody HolidayCalender holiday) throws CustomException {
        HolidayCalender createdHolidayCalender = holidayService.createHolidayCalender(holiday);
        return new ResponseEntity<>(createdHolidayCalender, HttpStatus.CREATED);
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayCalender> getHolidayCalender(@PathVariable Long holidayId) throws CustomException {
        HolidayCalender holiday = holidayService.getHolidayCalenderById(holidayId);
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    @GetMapping("/getHolidayCalenders")
    public ResponseEntity<List<HolidayCalender>> getAllHolidayCalenders() throws CustomException {
        List<HolidayCalender> holidays = holidayService.getAllHolidayCalenders();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @PutMapping("/update/{holidayId}")
    public ResponseEntity<HolidayCalender> updateHolidayCalender(@PathVariable Long holidayId, @RequestBody HolidayCalender holiday) throws CustomException {
        HolidayCalender updatedHolidayCalender = holidayService.updateHolidayCalender(holidayId, holiday);
        return new ResponseEntity<>(updatedHolidayCalender, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{holidayId}")
    public ResponseEntity<Void> deleteHolidayCalender(@PathVariable Long holidayId) throws CustomException {
        holidayService.deleteHolidayCalender(holidayId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
