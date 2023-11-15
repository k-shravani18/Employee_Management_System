package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long designationId;

    private String designationName;

    @ManyToOne
    private Department department;
}
