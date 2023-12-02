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
    private String employeeId;

    private String imageUrl;

    private String firstName;

    private String lastName;

    private String emailId;

    private int age;

    private String phone;

    private String gender;

    private Boolean isReportingManager;

    private String reportingManager;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private BranchLocation location;

}
