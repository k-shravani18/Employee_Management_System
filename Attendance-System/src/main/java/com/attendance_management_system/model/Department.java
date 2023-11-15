package com.attendance_management_system.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class  Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentId;

    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Designation> designations;

}
