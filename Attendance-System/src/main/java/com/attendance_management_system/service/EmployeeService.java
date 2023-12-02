package com.attendance_management_system.service;

import com.attendance_management_system.model.Employee;
import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {
    Employee createEmployee(Employee employee) throws CustomException;
    Employee getEmployeeByEmailId(String email) throws CustomException;
    Optional<Employee> getEmployeeById(String  id) throws CustomException;
    List<Employee> getAllEmployees() throws CustomException;
    Employee updateEmployee(String  email, Employee employee) throws CustomException;
    void deleteEmployee(String  employeeId) throws CustomException;

    List<Employee> getAllReportingManagers() throws CustomException;
}
