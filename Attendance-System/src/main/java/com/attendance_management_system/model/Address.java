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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;

    private int buildingNo;

    private String buildingName;

    private String streetName;

    private String city;

    private String district;

    private String state;

    private String country;

    private long postalCode;

}
