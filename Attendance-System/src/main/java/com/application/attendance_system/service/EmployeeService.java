package com.application.attendance_system.service;

import com.application.attendance_system.entity.Employee;
import com.application.attendance_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee createEmployee(Employee employee) throws CustomException;
    Employee getEmployeeById(Long employeeId) throws CustomException;
    List<Employee> getAllEmployees() throws CustomException;
    Employee updateEmployee(Long employeeId, Employee employee) throws CustomException;
    void deleteEmployee(Long employeeId) throws CustomException;
}
