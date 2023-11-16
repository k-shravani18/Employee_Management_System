package com.attendance_management_system.model;

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

    private String dayType;

    @OneToMany(mappedBy = "attendance")
    private List<AttendanceDetails> attendanceDetails;
}