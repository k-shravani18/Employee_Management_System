package com.attendance_management_system.service.serviceimpl;

import com.attendance_management_system.model.Address;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.repository.AddressRepository;
import com.attendance_management_system.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Creates a new address.
     * @param address The address to be created.
     * @return The created address.
     * @throws CustomException If there is an issue creating the address.
     * @author Kamil Praseej
     */
    @Override
    public Address createAddress(Address address) throws CustomException {
        try {
            return addressRepository.save(address);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create address.", e);
        }
    }

    /**
     * Retrieves an address by its ID.
     * @param addressId The ID of the address to be retrieved.
     * @return The address with the specified ID.
     * @throws CustomException If the address with the given ID is not found or if there is an issue fetching the address.
     * @author Kamil Praseej
     */
    @Override
    public Address getAddressById(Long addressId) throws CustomException {
        try {
            return addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch address.", e);
        }
    }

    /**
     * Updates an existing address.
     * @param addressId The ID of the address to be updated.
     * @param address   The updated address information.
     * @return The updated address.
     * @throws CustomException If there is an issue updating the address.
     * @author Kamil Praseej
     */
    @Override
    public Address updateAddress(Long addressId, Address address) throws CustomException {
        try {
            address.setAddressId(addressId);
            return addressRepository.save(address);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update address.", e);
        }
    }

    /**
     * Deletes an address by its ID.
     * @param addressId The ID of the address to be deleted.
     * @throws CustomException If there is an issue deleting the address.
     * @author Kamil Praseej
     */
    @Override
    public void deleteAddress(Long addressId) throws CustomException {
        try {
            addressRepository.deleteById(addressId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete address.", e);
        }
    }

}
