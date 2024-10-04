package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE e.id = :empId")
    List<Schedule> getScheduleByEmployeeId(@Param("empId") long empId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id = :petId")
    List<Schedule> getScheduleByPetId(@Param("petId") long petId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id IN :petIds")
    List<Schedule> getScheduleByCustomerIds(@Param("petIds") List<Long> petIds);
}
