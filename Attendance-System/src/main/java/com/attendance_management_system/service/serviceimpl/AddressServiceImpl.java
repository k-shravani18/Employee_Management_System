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

    @Override
    public Address createAddress(Address address) throws CustomException {
        try {
            return addressRepository.save(address);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to create address.", e);
        }
    }

    @Override
    public Address getAddressById(Long addressId) throws CustomException {
        try {
            return addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        } catch (DataAccessException e) {
            throw new CustomException("Failed to fetch address.", e);
        }
    }

    @Override
    public Address updateAddress(Long addressId, Address address) throws CustomException {
        try {
            address.setAddressId(addressId);
            return addressRepository.save(address);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to update address.", e);
        }
    }

    @Override
    public void deleteAddress(Long addressId) throws CustomException {
        try {
            addressRepository.deleteById(addressId);
        } catch (DataAccessException e) {
            throw new CustomException("Failed to delete address.", e);
        }
    }
}
