package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getId() != 0) {
            return new CustomerDTO();
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhoneNumber());
        customer.setNote(customerDTO.getNotes());

        if (customerDTO.getPetIds() != null && !customerDTO.getPetIds().isEmpty()) {
            List<Pet> petsOfCustomer = petRepository.getPetsByIds(customerDTO.getPetIds());
            customer.setPets(petsOfCustomer);
        }

        customer = customerRepository.save(customer);

        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null && !customer.getPets().isEmpty()) {
            petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        }

        CustomerDTO rs = new CustomerDTO();
        rs.setId(customer.getId());
        rs.setName(customer.getName());
        rs.setPhoneNumber(customer.getPhone());
        rs.setPetIds(petIds);
        rs.setNotes(customer.getNote());

        return rs;
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customers = customerRepository.getAllCustomer();
        List<CustomerDTO> rs = new ArrayList<>();
        rs = customers.stream().map(customer -> {
            List<Long> petIds = new ArrayList<>();
            if (customer.getPets() != null && !customer.getPets().isEmpty()) {
                petIds = customer.getPets().stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList());
            }
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setPhoneNumber(customer.getPhone());
            customerDTO.setPetIds(petIds);
            customerDTO.setNotes(customer.getNote());
            return customerDTO;
        }).collect(Collectors.toList());

        return rs;
    }

    @Override
    public CustomerDTO getOwnerByPet(long petId) {
        Customer customer = customerRepository.getOwnerByPet(petId);
        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null && !customer.getPets().isEmpty()) {
            petIds = customer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
        }
        CustomerDTO rs = new CustomerDTO();
        rs.setId(customer.getId());
        rs.setName(customer.getName());
        rs.setPhoneNumber(customer.getPhone());
        rs.setPetIds(petIds);
        rs.setNotes(customer.getNote());

        return rs;
    }
}
