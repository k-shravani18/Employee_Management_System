package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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

    private String emailId;

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
