package com.attendance_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HolidayCalender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long holidayId;

    private LocalDate holidayDate;

    private String holidayName;
}
