package com.attendance_management_system.controller;

import com.attendance_management_system.model.Department;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Creates a new department.
     * @param department The department information to be added.
     * @return The created department.
     * @throws CustomException If there is an issue creating the department.
     */
    @PostMapping("/add")
    public ResponseEntity<Department> createDepartment(
            @RequestBody Department department) throws CustomException {
        Department createdDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    /**
     * Retrieves a department by its ID.
     * @param departmentId The ID of the department to be retrieved.
     * @return The department with the specified ID.
     * @throws CustomException If the department with the given ID
       is not found or if there is an issue fetching the department.
     */
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartment(
            @PathVariable Long departmentId) throws CustomException {
        Department department = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * Retrieves all departments.
     * @return List of all departments.
     * @throws CustomException If there is an issue fetching the departments.
     */
    @GetMapping("/getDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() throws CustomException {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * Updates an existing department.
     * @param departmentId The ID of the department to be updated.
     * @param department   The updated department information.
     * @return The updated department.
     * @throws CustomException If there is an issue updating the department.
     */
    @PutMapping("/update/{departmentId}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long departmentId, @RequestBody Department department) throws CustomException {
        Department updatedDepartment = departmentService.updateDepartment(departmentId, department);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    /**
     * Deletes a department by its ID.
     * @param departmentId The ID of the department to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the department.
     */
    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) throws CustomException {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
