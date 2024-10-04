package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public PetDTO savePet(PetDTO petDTO) {
        PetDTO rs = new PetDTO();
        if (petDTO.getId() != 0) {
            return rs;
        }

        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNote(petDTO.getName());
        Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
        Customer customer = null;
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        }
        pet.setCustomer(customer);
        pet = petRepository.save(pet);

        List<Pet> pets = new ArrayList<>();
        if (customer != null) {
            if (customer.getPets() != null && !customer.getPets().isEmpty()) {
                pets = customer.getPets();
            }
            pets.add(pet);
            customer.setPets(pets);
            customerRepository.save(customer);
        }

        rs.setId(pet.getId());
        rs.setType(pet.getType());
        rs.setName(pet.getName());
        rs.setOwnerId(pet.getCustomer().getId());
        rs.setBirthDate(pet.getBirthDate());
        rs.setNotes(pet.getNote());

        return rs;
    }

    @Override
    public PetDTO getPetById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        PetDTO rs = new PetDTO();
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            rs.setId(pet.getId());
            rs.setType(pet.getType());
            rs.setName(pet.getName());
            rs.setOwnerId(pet.getCustomer().getId());
            rs.setBirthDate(pet.getBirthDate());
            rs.setNotes(pet.getNote());
        }

        return rs;
    }

    @Override
    public List<PetDTO> getPets() {
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> rs = new ArrayList<>();
        if (!pets.isEmpty()) {
            rs = pets.stream().map(pet -> {
                PetDTO petDTO = new PetDTO();
                petDTO.setId(pet.getId());
                petDTO.setType(pet.getType());
                petDTO.setName(pet.getName());
                petDTO.setOwnerId(pet.getCustomer().getId());
                petDTO.setBirthDate(pet.getBirthDate());
                petDTO.setNotes(pet.getNote());
                return petDTO;
            }).collect(Collectors.toList());
        }

        return rs;
    }

    @Override
    public List<PetDTO> getPetOfCustomer(Long customerId) {
        List<Pet> pets = petRepository.getPetsByOwnerId(customerId);
        List<PetDTO> rs = new ArrayList<>();
        if (pets != null && !pets.isEmpty()) {
            rs = pets.stream().map(pet -> {
                PetDTO petDTO = new PetDTO();
                petDTO.setId(pet.getId());
                petDTO.setType(pet.getType());
                petDTO.setName(pet.getName());
                petDTO.setOwnerId(pet.getCustomer().getId());
                petDTO.setBirthDate(pet.getBirthDate());
                petDTO.setNotes(pet.getNote());
                return petDTO;
            }).collect(Collectors.toList());
        }

        return rs;
    }
}
