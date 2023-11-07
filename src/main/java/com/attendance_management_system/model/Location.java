package com.attendance_management_system.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer locationId;
    private String LocationName;
    private String locationDetails;
}
