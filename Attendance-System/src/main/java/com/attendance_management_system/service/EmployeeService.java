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
//    Optional<Employee> getEmployeeById(long id) throws CustomException;
    List<Employee> getAllEmployees() throws CustomException;
    Employee updateEmployee(Long employeeId, Employee employee) throws CustomException;
    void deleteEmployee(Long employeeId) throws CustomException;
}
