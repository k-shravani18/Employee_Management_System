package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Designation;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.DesignationRepository;
import com.attendance_management_system.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Autowired
    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @Override
    public Designation createDesignation(Designation designation) throws CustomException {
        try {
            return designationRepository.save(designation);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create designation.", e);
        }
    }

    @Override
    public Designation getDesignationById(Long designationId) throws CustomException {
        try {
            return designationRepository.findById(designationId)
                    .orElseThrow(() -> new EntityNotFoundException("Designation not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch designation.", e);
        }
    }

    @Override
    public Designation updateDesignation(Long designationId, Designation designation) throws CustomException {
        try {
            designation.setDesignationId(designationId);
            return designationRepository.save(designation);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update designation.", e);
        }
    }

    @Override
    public void deleteDesignation(Long designationId) throws CustomException {
        try {
            designationRepository.deleteById(designationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete designation.", e);
        }
    }
}
