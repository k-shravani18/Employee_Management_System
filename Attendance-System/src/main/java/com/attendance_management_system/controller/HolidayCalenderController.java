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

    /**
     * Creates a new holiday in the holiday calendar.
     * @param holiday The holiday information to be added.
     * @return The created holiday in the calendar.
     * @throws CustomException If there is an issue creating the holiday.
     */
    @PostMapping("/add")
    public ResponseEntity<HolidayCalender> createHolidayCalender(
            @RequestBody HolidayCalender holiday) throws CustomException {
        HolidayCalender createdHolidayCalender = holidayService.createHolidayCalender(holiday);
        return new ResponseEntity<>(createdHolidayCalender, HttpStatus.CREATED);
    }

    /**
     * Retrieves a holiday from the holiday calendar by its ID.
     * @param holidayId The ID of the holiday to be retrieved.
     * @return The holiday with the specified ID.
     * @throws CustomException If the holiday with the given ID
       is not found or if there is an issue fetching the holiday.
     */
    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayCalender> getHolidayCalender(@PathVariable Long holidayId) throws CustomException {
        HolidayCalender holiday = holidayService.getHolidayCalenderById(holidayId);
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    /**
     * Retrieves all holidays from the holiday calendar.
     * @return A list of all holidays in the calendar.
     * @throws CustomException If there is an issue fetching the holidays.
     */
    @GetMapping("/getHolidayCalenders")
    public ResponseEntity<List<HolidayCalender>> getAllHolidayCalenders() throws CustomException {
        List<HolidayCalender> holidays = holidayService.getAllHolidayCalenders();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    /**
     * Updates an existing holiday in the holiday calendar.
     * @param holidayId The ID of the holiday to be updated.
     * @param holiday   The updated holiday information.
     * @return The updated holiday in the calendar.
     * @throws CustomException If there is an issue updating the holiday.
     */
    @PutMapping("/update/{holidayId}")
    public ResponseEntity<HolidayCalender> updateHolidayCalender(
            @PathVariable Long holidayId, @RequestBody HolidayCalender holiday) throws CustomException {
        HolidayCalender updatedHolidayCalender = holidayService.updateHolidayCalender(holidayId, holiday);
        return new ResponseEntity<>(updatedHolidayCalender, HttpStatus.OK);
    }

    /**
     * Deletes a holiday from the holiday calendar by its ID.
     * @param holidayId The ID of the holiday to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the holiday.
     */
    @DeleteMapping("/delete/{holidayId}")
    public ResponseEntity<Void> deleteHolidayCalender(@PathVariable Long holidayId) throws CustomException {
        holidayService.deleteHolidayCalender(holidayId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
