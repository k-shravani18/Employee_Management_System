package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Employee;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.EmployeeRepository;
import com.attendance_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) throws CustomException {
        try {
            return employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create employee.", e);
        }
    }

    @Override
    public Employee getEmployeeByEmailId(String email) throws CustomException {
        try {
            return employeeRepository.findByEmailId(email);

        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employee.", e);
        }
    }

//    @Override
//    public Optional<Employee> getEmployeeById(long id) throws CustomException {
//        try {
//            return employeeRepository.findById(id);
//
//        } catch (DataAccessException e) {
//            throw new CustomException("Failed to fetch employee.", e);
//        }    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {
        try {
            return employeeRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employees.", e);
        }
    }

    @Override
    public List<Employee> getAllReportingManagers() throws CustomException {
        try {
            return employeeRepository.findAll()
                    .stream()
                    .filter(employee -> employee.getIsReportingManager())
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch reporting managers.", e);
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
