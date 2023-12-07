package com.attendance_management_system.controller;

import com.attendance_management_system.model.Designation;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@CrossOrigin("*")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    /**
     * Creates a new designation.
     * @param designation The designation information to be added.
     * @return The created designation.
     * @throws CustomException If there is an issue creating the designation.
     */
    @PostMapping("/add")
    public ResponseEntity<Designation> createDesignation(
            @RequestBody Designation designation) throws CustomException, RuntimeException {
        Designation createdDesignation = designationService.createDesignation(designation);
        return new ResponseEntity<>(createdDesignation, HttpStatus.CREATED);
    }

    /**
     * Retrieves a designation by its ID.
     * @param designationId The ID of the designation to be retrieved.
     * @return The designation with the specified ID.
     * @throws CustomException If the designation with the given ID
       is not found or if there is an issue fetching the designation.
     */
    @GetMapping("/{designationId}")
    public ResponseEntity<Designation> getDesignation(@PathVariable Long designationId) throws CustomException {
        Designation designation = designationService.getDesignationById(designationId);
        return new ResponseEntity<>(designation, HttpStatus.OK);
    }

    /**
     * Retrieves all designations.
     * @return List of all designations.
     * @throws CustomException If there is an issue fetching the designations.
     */
    @GetMapping("/getDesignations")
    public ResponseEntity<List<Designation>> getAllDesignations() throws CustomException {
        List<Designation> designations = designationService.getAllDesignations();
        return new ResponseEntity<>(designations, HttpStatus.OK);
    }

    /**
     * Updates an existing designation.
     * @param designationId The ID of the designation to be updated.
     * @param designation   The updated designation information.
     * @return The updated designation.
     * @throws CustomException If there is an issue updating the designation.
     */
    @PutMapping("/update/{designationId}")
    public ResponseEntity<Designation> updateDesignation(
            @PathVariable Long designationId, @RequestBody Designation designation) throws CustomException {
        Designation updatedDesignation = designationService.updateDesignation(designationId, designation);
        return new ResponseEntity<>(updatedDesignation, HttpStatus.OK);
    }

    /**
     * Deletes a designation by its ID.
     * @param designationId The ID of the designation to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the designation.
     */
    @DeleteMapping("/delete/{designationId}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long designationId) throws CustomException {
        designationService.deleteDesignation(designationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
