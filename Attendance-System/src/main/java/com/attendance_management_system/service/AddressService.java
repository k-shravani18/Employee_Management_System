package com.attendance_management_system.service;

import com.attendance_management_system.model.Address;
import com.attendance_management_system.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address createAddress(Address address) throws CustomException;
    Address getAddressById(Long addressId) throws CustomException;
    Address updateAddress(Long addressId, Address address) throws CustomException;
    void deleteAddress(Long addressId) throws CustomException;
}
