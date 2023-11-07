package com.application.attendance_system.service.serviceimpl;

import com.application.attendance_system.entity.Department;
import com.application.attendance_system.exceptions.CustomException;
import com.application.attendance_system.repository.DepartmentRepository;
import com.application.attendance_system.service.DepartmentService;
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
