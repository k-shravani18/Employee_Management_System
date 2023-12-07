package com.attendance_management_system.controller;

import com.attendance_management_system.model.Department;
import com.attendance_management_system.model.Organization;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@CrossOrigin("*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * Creates a new organization.
     * @param organization The organization to be created.
     * @return The created organization.
     * @throws CustomException If there is an issue creating the organization.
     */
    @PostMapping("/add")
    public ResponseEntity<Organization> createOrganization(
            @RequestBody Organization organization) throws CustomException {
        Organization createdOrganization = organizationService.createOrganization(organization);
        return new ResponseEntity<>(createdOrganization, HttpStatus.CREATED);
    }

    /**
     * Retrieves an organization by its ID.
     * @param organizationId The ID of the organization to be retrieved.
     * @return The organization with the specified ID.
     * @throws CustomException If the organization with the given ID
       is not found or if there is an issue fetching the organization.
     */
    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long organizationId) throws CustomException {
        Organization organization = organizationService.getOrganizationById(organizationId);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    /**
     * Retrieves all organizations.
     * @return List of all organizations.
     * @throws CustomException If there is an issue fetching the organization.
     */
    @GetMapping("/getOrganizations")
    public ResponseEntity<List<Organization>> getAllOrganizations() throws CustomException {
        List<Organization> organizations = organizationService.getAllOrganizations();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

    /**
     * Updates an existing organization.
     * @param organizationId The ID of the organization to be updated.
     * @param organization   The updated organization information.
     * @return The updated organization.
     * @throws CustomException If there is an issue updating the organization.
     */
    @PutMapping("/update/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable Long organizationId, @RequestBody Organization organization) throws CustomException {
        Organization updatedOrganization = organizationService.updateOrganization(organizationId, organization);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }

    /**
     * Deletes an organization by its ID.
     * @param organizationId The ID of the organization to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the organization.
     */
    @DeleteMapping("/delete/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long organizationId) throws CustomException {
        organizationService.deleteOrganization(organizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
