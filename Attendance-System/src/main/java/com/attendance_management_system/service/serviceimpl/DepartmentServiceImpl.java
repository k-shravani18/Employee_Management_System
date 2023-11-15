package com.attendance_management_system.service.serviceimpl;

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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) throws CustomException {
        try {
            return departmentRepository.save(department);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create department.", e);
        }
    }

    @Override
    public Department getDepartmentById(Long departmentId) throws CustomException {
        try {
            return departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch department.", e);
        }
    }

    @Override
    public List<Department> getAllDepartments() throws CustomException {
        try {
            return departmentRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch department.", e);
        }
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) throws CustomException {
        try {
            department.setDepartmentId(departmentId);
            return departmentRepository.save(department);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update department.", e);
        }
    }

    @Override
    public void deleteDepartment(Long departmentId) throws CustomException {
        try {
            departmentRepository.deleteById(departmentId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete department.", e);
        }
    }
}
