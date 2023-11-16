package com.attendance_management_system.model;

import com.attendance_management_system.constants.DayType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attendanceId;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private DayType dayType;

    @OneToMany(mappedBy = "attendance")
    private List<AttendanceDetails> attendanceDetails;
}