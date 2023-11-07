package com.application.attendance_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeId;

    private String fullName;

    private String firstName;

    private String lastName;

    private String age;

    private String phone;

    private String gender;

    @OneToOne
    private Address address;

    @OneToOne
    private Designation designation;

//    @OneToMany
//    private LeavePolicy leavePolicy;
}
