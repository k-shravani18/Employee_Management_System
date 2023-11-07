package com.attendance_management_system.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class LeaveApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer leaveAppId;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;

    @ManyToOne
    private LeavePolicy leavePolicy;

//    @ManyToOne
//    private Employee employee
}
