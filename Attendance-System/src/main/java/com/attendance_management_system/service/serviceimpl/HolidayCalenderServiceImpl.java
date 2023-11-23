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

    private final HolidayCalenderRepository holidayRepository;

    @Autowired
    public HolidayCalenderServiceImpl(HolidayCalenderRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    /**
     * Creates a new holiday in the calendar.
     * @param holiday The holiday to be created.
     * @return The created holiday.
     * @throws CustomException If there is an issue creating the holiday.
     */
    @Override
    public HolidayCalender createHolidayCalender(HolidayCalender holiday) throws CustomException {
        try {
            return holidayRepository.save(holiday);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create holiday.", e);
        }
    }

    /**
     * Retrieves a holiday from the calendar by its ID.
     * @param holidayId The ID of the holiday to be retrieved.
     * @return The holiday with the specified ID.
     * @throws CustomException If the holiday with the given ID
       is not found or if there is an issue fetching the holiday.
     */
    @Override
    public HolidayCalender getHolidayCalenderById(Long holidayId) throws CustomException {
        try {
            return holidayRepository.findById(holidayId)
                    .orElseThrow(() -> new EntityNotFoundException("HolidayCalender not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch holiday.", e);
        }
    }

    /**
     * Retrieves all holidays from the calendar.
     * @return A list of all holidays in the calendar.
     * @throws CustomException If there is an issue fetching the holidays.
     */
    @Override
    public List<HolidayCalender> getAllHolidayCalenders() throws CustomException {
        try {
            return holidayRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch holiday.", e);
        }
    }

    /**
     * Updates an existing holiday in the calendar.
     * @param holidayId The ID of the holiday to be updated.
     * @param holiday   The updated holiday information.
     * @return The updated holiday.
     * @throws CustomException If there is an issue updating the holiday.
     */
    @Override
    public HolidayCalender updateHolidayCalender(Long holidayId, HolidayCalender holiday) throws CustomException {
        try {
            holiday.setHolidayId(holidayId);
            return holidayRepository.save(holiday);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update holiday.", e);
        }
    }

    /**
     * Deletes a holiday from the calendar by its ID.
     * @param holidayId The ID of the holiday to be deleted.
     * @throws CustomException If there is an issue deleting the holiday.
     */
    @Override
    public void deleteHolidayCalender(Long holidayId) throws CustomException {
        try {
            holidayRepository.deleteById(holidayId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete holiday.", e);
        }
    }

}
