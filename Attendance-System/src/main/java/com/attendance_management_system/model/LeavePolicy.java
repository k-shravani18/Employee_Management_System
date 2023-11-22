package com.attendance_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LeavePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long leavePolicyId;
    private String leaveType;
    private int maximumLeaves;


}
