package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.id IN :ids")
    List<Pet> getPetsByIds(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT p FROM Pet p LEFT JOIN p.schedules WHERE p.id IN :ids")
    List<Pet> getPetsByIdsJoinSchedules(@Param("ids") List<Long> ids);

    @Query("SELECT p FROM Pet p WHERE p.customer.id = :ownerId")
    List<Pet> getPetsByOwnerId(@Param("ownerId") long ownerId);
}
