package com.attendance_management_system.repository;

import com.attendance_management_system.constants.HolidayType;
import com.attendance_management_system.model.Department;
import com.attendance_management_system.model.HolidayCalender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidayCalenderRepository extends JpaRepository<HolidayCalender, Long> {
    boolean existsByHolidayDateAndHolidayType(LocalDate holidayDate, HolidayType holidayType);
}

