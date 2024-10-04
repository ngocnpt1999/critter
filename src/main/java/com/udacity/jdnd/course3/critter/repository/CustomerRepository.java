package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN c.pets")
    List<Customer> getAllCustomer();

    @Query("SELECT c FROM Customer c JOIN c.pets p WHERE p.id = :petId")
    Customer getOwnerByPet(@Param("petId") long petId);
}
