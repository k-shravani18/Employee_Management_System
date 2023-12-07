package com.attendance_management_system.service;

import com.attendance_management_system.model.Designation;
import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DesignationService {
    Designation createDesignation(Designation designation) throws CustomException;
    Designation getDesignationById(Long designationId) throws CustomException;
    List<Designation> getAllDesignations() throws CustomException;
    Designation updateDesignation(Long designationId, Designation designation) throws CustomException;
    void deleteDesignation(Long designationId) throws CustomException;
}
