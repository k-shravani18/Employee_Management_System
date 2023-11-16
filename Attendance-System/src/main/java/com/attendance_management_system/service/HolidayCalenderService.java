package com.attendance_management_system.service;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.HolidayCalender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayCalenderService {
    HolidayCalender createHolidayCalender(HolidayCalender holiday) throws CustomException;
    HolidayCalender getHolidayCalenderById(Long holidayId) throws CustomException;

    List<HolidayCalender> getAllHolidayCalenders() throws CustomException;
    HolidayCalender updateHolidayCalender(Long holidayId, HolidayCalender holiday) throws CustomException;
    void deleteHolidayCalender(Long holidayId) throws CustomException;
}
