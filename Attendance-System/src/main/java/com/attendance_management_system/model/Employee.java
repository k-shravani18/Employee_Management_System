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

    private String imageUrl;

    private String firstName;

    private String lastName;

    private String emailId;

    private int age;

    private String phone;

    private String gender;

    private Boolean isReportingManager;

    private String reportingManager;

    @OneToOne
    private Address address;

    @OneToOne
    private Designation designation;

    @OneToOne
    private BranchLocation location;

}
