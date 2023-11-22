package com.attendance_management_system.service;

import com.attendance_management_system.model.Organization;
import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface OrganizationService {

    Organization createOrganization(Organization organization) throws CustomException;
    Organization getOrganizationById(Long organizationId) throws CustomException;
    Organization updateOrganization(Long organizationId, Organization organization) throws CustomException;
    void deleteOrganization(Long organizationId) throws CustomException;
}
