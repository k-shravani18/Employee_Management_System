package com.attendance_management_system.model;

import com.attendance_management_system.constants.HolidayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private HolidayType holidayType;
}
