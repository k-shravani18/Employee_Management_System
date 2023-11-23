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

    /**
     * Creates a new employee.
     * @param employee The employee object to be created.
     * @return The created employee.
     * @throws CustomException If there is an issue creating the employee.
     */
    @Override
    public Employee createEmployee(Employee employee) throws CustomException {
        try {
            return employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create employee.", e);
        }
    }

    /**
     * Retrieves an employee by their email address.
     * @param email The email address of the employee.
     * @return The employee with the specified email address.
     * @throws CustomException If there is an issue fetching the employee.
     */
    @Override
    public Employee getEmployeeByEmailId(String email) throws CustomException {
        try {
            return employeeRepository.findByEmailId(email);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employee.", e);
        }
    }

    /**
     * Retrieves an employee by their ID.
     * @param id The ID of the employee.
     * @return An optional containing the employee with the specified ID, if present.
     * @throws CustomException If there is an issue fetching the employee.
     */
    @Override
    public Optional<Employee> getEmployeeById(long id) throws CustomException {
        try {
            return employeeRepository.findById(id);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employee.", e);
        }
    }

    /**
     * Retrieves a list of all employees.
     * @return The list of all employees.
     * @throws CustomException If there is an issue fetching the employees.
     */
    @Override
    public List<Employee> getAllEmployees() throws CustomException {
        try {
            return employeeRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch employees.", e);
        }
    }

    /**
     * Retrieves a list of all reporting managers.
     * @return The list of all reporting managers.
     * @throws CustomException If there is an issue fetching the reporting managers.
     */
    @Override
    public List<Employee> getAllReportingManagers() throws CustomException {
        try {
            return employeeRepository.findAll()
                    .stream()
                    .filter(Employee::getIsReportingManager)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch reporting managers.", e);
        }
    }

    /**
     * Updates an existing employee with the specified email address.
     * @param email    The email address of the employee to be updated.
     * @param employee The updated employee details.
     * @return The updated employee.
     * @throws CustomException If there is an issue updating the employee.
     */
    @Override
    public Employee updateEmployee(String email, Employee employee) throws CustomException {
        try {
            Employee employeeDetails = employeeRepository.findByEmailId(email);
            employee.setEmployeeId(employeeDetails.getEmployeeId());
            return employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update employee.", e);
        }
    }

    /**
     * Deletes an employee with the specified ID.
     * @param employeeId The ID of the employee to be deleted.
     * @throws CustomException If there is an issue deleting the employee.
     */
    @Override
    public void deleteEmployee(Long employeeId) throws CustomException {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete employee.", e);
        }
    }
}
