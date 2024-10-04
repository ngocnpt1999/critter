package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomer();

    CustomerDTO getOwnerByPet(long petId);
}
