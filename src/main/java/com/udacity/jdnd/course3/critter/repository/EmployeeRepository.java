package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN e.daysAvailable da JOIN e.skills s " +
            "WHERE s IN :skills AND da = :daysAvailable " +
            "GROUP BY e HAVING COUNT(s) = :skillsCount")
    List<Employee> findEmployeesForService(@Param("daysAvailable") DayOfWeek daysAvailable,
                                           @Param("skills") Set<EmployeeSkill> skills,
                                           @Param("skillsCount") long skillsCount);

    @Query("SELECT e FROM Employee e WHERE e.id IN :ids")
    List<Employee> getEmployeesByIds(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN e.schedules WHERE e.id IN :ids")
    List<Employee> getEmployeesByIdsJoinSchedules(@Param("ids") List<Long> ids);
}
