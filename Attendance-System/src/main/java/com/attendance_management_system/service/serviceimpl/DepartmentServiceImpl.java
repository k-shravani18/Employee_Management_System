package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.exceptions.DepartmentAlreadyExistsException;
import com.attendance_management_system.model.Department;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.DepartmentRepository;
import com.attendance_management_system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Creates a new department.
     * @param department The department to be created.
     * @return The created department.
     * @throws CustomException If there is an issue creating the department.
     */
    @Override
    public Department createDepartment(Department department) throws CustomException {
        try {
            if(!departmentRepository.existsByDepartmentName(department.getDepartmentName())){
            return departmentRepository.save(department);}
            else {
                throw new DepartmentAlreadyExistsException("The department is already exists");
            }
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create department.", e);
        } catch (DepartmentAlreadyExistsException e) {
            throw new RuntimeException("The department is already exists",e);
        }
    }

    /**
     * Retrieves a department by its ID.
     * @param departmentId The ID of the department to be retrieved.
     * @return The department with the specified ID.
     * @throws CustomException If the department with the given ID
       is not found or if there is an issue fetching the department.
     */
    @Override
    public Department getDepartmentById(Long departmentId) throws CustomException {
        try {
            return departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch department.", e);
        }
    }

    /**
     * Retrieves all departments.
     * @return A list of all departments.
     * @throws CustomException If there is an issue fetching the departments.
     */
    @Override
    public List<Department> getAllDepartments() throws CustomException {
        try {
            return departmentRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch department.", e);
        }
    }

    /**
     * Updates an existing department.
     * @param departmentId The ID of the department to be updated.
     * @param department   The updated department information.
     * @return The updated department.
     * @throws CustomException If there is an issue updating the department.
     */
    @Override
    public Department updateDepartment(Long departmentId, Department department) throws CustomException {
        try {
            department.setDepartmentId(departmentId);
            return departmentRepository.save(department);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update department.", e);
        }
    }

    /**
     * Deletes a department by its ID.
     * @param departmentId The ID of the department to be deleted.
     * @throws CustomException If there is an issue deleting the department.
     */
    @Override
    public void deleteDepartment(Long departmentId) throws CustomException {
        try {
            departmentRepository.deleteById(departmentId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete department.", e);
        }
    }

}
