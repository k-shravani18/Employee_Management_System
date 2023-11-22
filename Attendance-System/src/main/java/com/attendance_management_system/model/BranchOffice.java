package com.attendance_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long branchOfficeId;

    private String LocatedCity;

    private String locationAddress;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

}
