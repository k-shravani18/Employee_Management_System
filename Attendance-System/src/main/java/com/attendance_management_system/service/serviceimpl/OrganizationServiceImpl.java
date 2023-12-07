package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Organization;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.OrganizationRepository;
import com.attendance_management_system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Creates a new organization.
     * @param organization The organization to be created.
     * @return The created organization.
     * @throws CustomException If there is an issue creating the organization.
     */
    @Override
    public Organization createOrganization(Organization organization) throws CustomException {
        try {
            return organizationRepository.save(organization);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create organization.", e);
        }
    }

    /**
     * Retrieves an organization by its ID.
     * @param organizationId The ID of the organization to be retrieved.
     * @return The organization with the specified ID.
     * @throws CustomException If the organization with the given ID
       is not found or if there is an issue fetching the organization.
     */
    @Override
    public Organization getOrganizationById(Long organizationId) throws CustomException {
        try {
            return organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch organization.", e);
        }
    }

    /**
     * Retrieves all organizations.
     * @return A list of all organizations.
     * @throws CustomException If there is an issue fetching the organizations.
     */
    @Override
    public List<Organization> getAllOrganizations() throws CustomException {
        return organizationRepository.findAll();
    }

    /**
     * Updates an existing organization.
     * @param organizationId The ID of the organization to be updated.
     * @param organization   The updated organization information.
     * @return The updated organization.
     * @throws CustomException If there is an issue updating the organization.
     */
    @Override
    public Organization updateOrganization(
            Long organizationId, Organization organization) throws CustomException {
        try {
            organization.setOrganizationId(organizationId);
            return organizationRepository.save(organization);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update organization.", e);
        }
    }

    /**
     * Deletes an organization by its ID.
     * @param organizationId The ID of the organization to be deleted.
     * @throws CustomException If there is an issue deleting the organization.
     */
    @Override
    public void deleteOrganization(Long organizationId) throws CustomException {
        try {
            organizationRepository.deleteById(organizationId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete organization.", e);
        }
    }

}
