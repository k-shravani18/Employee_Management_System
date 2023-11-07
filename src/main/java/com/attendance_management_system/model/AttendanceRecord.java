package com.attendance_management_system.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attendanceRecordId;
    private LocalDate checkInTime;
    private LocalDate checkOutTime;
    private Date date;
    private String status;
    @OneToOne
    private Location location;
//    @ManyToOne
//    private Employee employee;

}
