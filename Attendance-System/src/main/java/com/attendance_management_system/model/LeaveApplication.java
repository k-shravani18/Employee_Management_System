package com.attendance_management_system.model;

import com.attendance_management_system.constants.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long leaveApplicationId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String teamEmailId;
    private String reason;
    private String status;
    private int noOfDays;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeavePolicy leavePolicy;


}
