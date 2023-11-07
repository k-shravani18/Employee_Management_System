package com.attendance_management_system.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class LeavePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer leavePolicyId;
    private String leaveType;
    private Integer totalLeaves;
    private Integer leavesPerMonth;
    private Integer carryForwardLeaves;

    @OneToMany(mappedBy = "leavePolicy")
    private List<LeaveApp> leaveApplications;

}
