package com.attendance_management_system.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeLeave {
    private String leaveType;
    private int availableLeave;
    private int leaveTaken;
}
