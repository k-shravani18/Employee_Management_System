package com.attendance_management_system.controller;

import com.attendance_management_system.model.Address;
import com.attendance_management_system.exceptions.CustomException;
import com.attendance_management_system.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * Creates a new address.
     * @param address The address information to be added.
     * @return The created address.
     * @throws CustomException If there is an issue creating the address.
     * @author Kamil Praseej
     */
    @PostMapping("/add")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) throws CustomException {
        Address createdAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    /**
     * Retrieves an address by its ID.
     * @param addressId The ID of the address to be retrieved.
     * @return The address with the specified ID.
     * @throws CustomException If the address with the given ID
       is not found or if there is an issue fetching the address.
     * @author Kamil Praseej
     */
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable Long addressId) throws CustomException {
        Address address = addressService.getAddressById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * Updates an existing address.
     * @param addressId The ID of the address to be updated.
     * @param address   The updated address information.
     * @return The updated address.
     * @throws CustomException If there is an issue updating the address.
     * @author Kamil Praseej
     */
    @PutMapping("/update/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address address) throws CustomException {
        Address updatedAddress = addressService.updateAddress(addressId, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * Deletes an address by its ID.
     * @param addressId The ID of the address to be deleted.
     * @return ResponseEntity with status NO_CONTENT.
     * @throws CustomException If there is an issue deleting the address.
     * @author Kamil Praseej
     */
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) throws CustomException {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
