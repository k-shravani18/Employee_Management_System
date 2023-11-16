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
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) throws CustomException {
        Department createdDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long departmentId) throws CustomException {
        Department department = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() throws CustomException {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @PutMapping("/update/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long departmentId, @RequestBody Department department) throws CustomException {
        Department updatedDepartment = departmentService.updateDepartment(departmentId, department);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) throws CustomException {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
