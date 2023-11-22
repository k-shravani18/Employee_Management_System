package com.attendance_management_system.controller;

import com.attendance_management_system.model.Employee;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint to add a new employee
     * @param employee New employee data
     * @return ResponseEntity with the created employee
     * @throws CustomException if there is an issue creating the employee
     * @author Kamil Praseej
     */
    @PostMapping("/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws CustomException {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get employee details by email
     * @param email Employee email
     * @return ResponseEntity with the employee details
     * @throws CustomException if the employee is not found
     * @author Kamil Praseej
     */
    @GetMapping("/getEmployee/{email:.+}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String email) throws CustomException {
        Employee employee = employeeService.getEmployeeByEmailId(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Endpoint to get details of all employees
     * @return ResponseEntity with a list of all employees
     * @throws CustomException if there is an issue fetching employee details
     * @author Kamil Praseej
     */
    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() throws CustomException {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Endpoint to get all reporting managers
     * @return ResponseEntity with a list of reporting managers
     * @throws CustomException if there is an issue fetching reporting managers
     * @author Kamil Praseej
     */
    @GetMapping("/getReportingManagers")
    public ResponseEntity<List<Employee>> getAllReportingManagers() throws CustomException {
        List<Employee> employees = employeeService.getAllReportingManagers();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Endpoint to update employee details by email
     * @param email    Employee email
     * @param employee Updated employee data
     * @return ResponseEntity with the updated employee
     * @throws CustomException if there is an issue updating the employee
     * @author Kamil Praseej
     */
    @PutMapping("/update/{email:.+}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String email, @RequestBody Employee employee) throws CustomException {
        Employee updatedEmployee = employeeService.updateEmployee(email, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    /**
     * Endpoint to delete an employee by employeeId
     * @param employeeId Employee ID to be deleted
     * @return ResponseEntity indicating success (NO_CONTENT)
     * @throws CustomException if there is an issue deleting the employee
     * @author Kamil Praseej
     */
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) throws CustomException {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
