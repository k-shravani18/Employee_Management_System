package com.attendance_management_system.model;

import com.attendance_management_system.constants.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 500)
    private String checkInLocation;

    @Column(length = 500)
    private String checkOutLocation;

    private long totalTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @OneToOne
    private Employee employee;

    @ManyToOne
    @JsonIgnore
    private Attendance attendance;
}