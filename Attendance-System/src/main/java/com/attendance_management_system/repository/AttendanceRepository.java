package com.attendance_management_system.repository;

import com.attendance_management_system.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

//    @Query("SELECT a FROM Attendance a JOIN FETCH a.attendanceDetails WHERE a.date = :date")
//    Attendance findByDate(@Param("date") LocalDate date);
    Attendance findByDate(LocalDate date);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);


}
