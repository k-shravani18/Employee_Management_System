package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Organization;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.OrganizationRepository;
import com.attendance_management_system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization createOrganization(Organization organization) throws CustomException {
        try {
            return organizationRepository.save(organization);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create organization.", e);
        }
    }

    @Override
    public Organization getOrganizationById(Long organizationId) throws CustomException {
        try {
            return organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch organization.", e);
        }
    }

    @Override
    public Organization updateOrganization(Long organizationId, Organization organization) throws CustomException {
        try {
            organization.setOrganizationId(organizationId);
            return organizationRepository.save(organization);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update organization.", e);
        }
    }

    @Override
    public void deleteOrganization(Long organizationId) throws CustomException {
        try {
            organizationRepository.deleteById(organizationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete organization.", e);
        }
    }
}
