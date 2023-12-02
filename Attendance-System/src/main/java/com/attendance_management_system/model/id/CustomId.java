package com.attendance_management_system.model.id;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomId {
    @Id
    private String id;
    private int nextEmployeeId;
}
