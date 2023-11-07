package com.application.attendance_system.controller;

import com.application.attendance_system.entity.Designation;
import com.application.attendance_system.exceptions.CustomException;
import com.application.attendance_system.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/designations")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping("/add")
    public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) throws CustomException {
        Designation createdDesignation = designationService.createDesignation(designation);
        return new ResponseEntity<>(createdDesignation, HttpStatus.CREATED);
    }

    @GetMapping("/{designationId}")
    public ResponseEntity<Designation> getDesignation(@PathVariable Long designationId) throws CustomException {
        Designation designation = designationService.getDesignationById(designationId);
        return new ResponseEntity<>(designation, HttpStatus.OK);
    }

    @PutMapping("/update/{designationId}")
    public ResponseEntity<Designation> updateDesignation(@PathVariable Long designationId, @RequestBody Designation designation) throws CustomException {
        Designation updatedDesignation = designationService.updateDesignation(designationId, designation);
        return new ResponseEntity<>(updatedDesignation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{designationId}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long designationId) throws CustomException {
        designationService.deleteDesignation(designationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
