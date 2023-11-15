package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attendanceDetailsId;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private String checkInLocation;

    private String checkOutLocation;

    private long totalTime;

    @OneToOne
    private Employee employee;

    @ManyToOne
    private Attendance attendance;
}