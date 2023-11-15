package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long organizationId;

    private String organizationName;

    private String location;

    private String generalCheckInTime;

    private String generalCheckOutTime;
}
