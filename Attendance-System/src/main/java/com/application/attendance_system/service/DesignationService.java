package com.application.attendance_system.service;

import com.application.attendance_system.entity.Designation;
import com.application.attendance_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface DesignationService {
    Designation createDesignation(Designation designation) throws CustomException;
    Designation getDesignationById(Long designationId) throws CustomException;
    Designation updateDesignation(Long designationId, Designation designation) throws CustomException;
    void deleteDesignation(Long designationId) throws CustomException;
}
