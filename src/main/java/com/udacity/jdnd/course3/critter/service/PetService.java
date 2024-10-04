package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetDTO;

import java.util.List;

public interface PetService {

    PetDTO savePet(PetDTO petDTO);

    PetDTO getPetById(Long id);

    List<PetDTO> getPets();

    List<PetDTO> getPetOfCustomer(Long customerId);
}
