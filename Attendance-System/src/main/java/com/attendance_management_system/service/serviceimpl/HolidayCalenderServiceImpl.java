package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.model.HolidayCalender;
import com.attendance_management_system.repository.HolidayCalenderRepository;
import com.attendance_management_system.service.HolidayCalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class HolidayCalenderServiceImpl implements HolidayCalenderService {

    @Autowired
    private HolidayCalenderRepository holidayRepository;

    @Override
    public HolidayCalender createHolidayCalender(HolidayCalender holiday) throws CustomException {
        try {
            return holidayRepository.save(holiday);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create holiday.", e);
        }
    }

    @Override
    public HolidayCalender getHolidayCalenderById(Long holidayId) throws CustomException {
        try {
            return holidayRepository.findById(holidayId)
                    .orElseThrow(() -> new EntityNotFoundException("HolidayCalender not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch holiday.", e);
        }
    }

    @Override
    public List<HolidayCalender> getAllHolidayCalenders() throws CustomException {
        try {
            return holidayRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch holiday.", e);
        }
    }

    @Override
    public HolidayCalender updateHolidayCalender(Long holidayId, HolidayCalender holiday) throws CustomException {
        try {
            holiday.setHolidayId(holidayId);
            return holidayRepository.save(holiday);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update holiday.", e);
        }
    }

    @Override
    public void deleteHolidayCalender(Long holidayId) throws CustomException {
        try {
            holidayRepository.deleteById(holidayId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete holiday.", e);
        }
    }
}
