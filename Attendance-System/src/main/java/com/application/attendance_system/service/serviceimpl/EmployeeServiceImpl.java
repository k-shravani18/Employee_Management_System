package com.application.attendance_system.service.serviceimpl;

import com.application.attendance_system.entity.Employee;
import com.application.attendance_system.exceptions.CustomException;
import com.application.attendance_system.repository.EmployeeRepository;
import com.application.attendance_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) throws CustomException {
        try {
            return employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create employee.", e);
        }
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws CustomException {
        try {
            return employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employee.", e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {
        try {
            return employeeRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employees.", e);
        }
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employee) throws CustomException {
        try {
            employee.setEmployeeId(employeeId);
            return employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update employee.", e);
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) throws CustomException {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete employee.", e);
        }
    }
}
