package com.attendance_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BranchLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long locationId;

    private String locationName;

    private String locationDetails;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    @ManyToOne
    private Organization organization;

}

