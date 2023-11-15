package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long locationId;

        private String locationName;

        private String locationDetails;
    }
