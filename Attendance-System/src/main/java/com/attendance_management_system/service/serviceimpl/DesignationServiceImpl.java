package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.exceptions.DepartmentAlreadyExistsException;
import com.attendance_management_system.exceptions.DesignationAlreadyExistsException;
import com.attendance_management_system.model.Designation;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.DesignationRepository;
import com.attendance_management_system.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Autowired
    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * Creates a new designation.
     * @param designation The designation to be created.
     * @return The created designation.
     * @throws CustomException If there is an issue creating the designation.
     */
    @Override
    public Designation createDesignation(Designation designation) throws CustomException {
        try {
            if(!designationRepository.existsByDesignationName(designation.getDesignationName())){
                return designationRepository.save(designation);}
            else {
                throw new DesignationAlreadyExistsException("The designation is already exists");
            }
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create designation.", e);
        } catch (DesignationAlreadyExistsException e) {
            throw new RuntimeException("The designation is already exists",e);
        }
    }

    /**
     * Retrieves a designation by its ID.
     * @param designationId The ID of the designation to be retrieved.
     * @return The designation with the specified ID.
     * @throws CustomException If the designation with the given ID
       is not found or if there is an issue fetching the designation.
     */
    @Override
    public Designation getDesignationById(Long designationId) throws CustomException {
        try {
            return designationRepository.findById(designationId)
                    .orElseThrow(() -> new EntityNotFoundException("Designation not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch designation.", e);
        }
    }

    /**
     * Retrieves all designations.
     * @return A list of all designations.
     * @throws CustomException If there is an issue fetching the designations.
     */
    @Override
    public List<Designation> getAllDesignations() throws CustomException {
        return designationRepository.findAll();
    }

    /**
     * Updates an existing designation.
     * @param designationId The ID of the designation to be updated.
     * @param designation   The updated designation information.
     * @return The updated designation.
     * @throws CustomException If there is an issue updating the designation.
     */
    @Override
    public Designation updateDesignation(Long designationId, Designation designation) throws CustomException {
        try {
            designation.setDesignationId(designationId);
            return designationRepository.save(designation);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update designation.", e);
        }
    }

    /**
     * Deletes a designation by its ID.
     * @param designationId The ID of the designation to be deleted.
     * @throws CustomException If there is an issue deleting the designation.
     */
    @Override
    public void deleteDesignation(Long designationId) throws CustomException {
        try {
            designationRepository.deleteById(designationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete designation.", e);
        }
    }
}
