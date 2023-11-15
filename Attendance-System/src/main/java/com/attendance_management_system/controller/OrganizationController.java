package com.attendance_management_system.controller;

import com.attendance_management_system.model.Organization;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/add")
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) throws CustomException {
        Organization createdOrganization = organizationService.createOrganization(organization);
        return new ResponseEntity<>(createdOrganization, HttpStatus.CREATED);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long organizationId) throws CustomException {
        Organization organization = organizationService.getOrganizationById(organizationId);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    @PutMapping("/update/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long organizationId, @RequestBody Organization organization) throws CustomException {
        Organization updatedOrganization = organizationService.updateOrganization(organizationId, organization);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long organizationId) throws CustomException {
        organizationService.deleteOrganization(organizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
