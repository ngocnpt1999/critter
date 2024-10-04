package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() != 0) {
            return new EmployeeDTO();
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());

        employee = employeeRepository.save(employee);

        EmployeeDTO rs = new EmployeeDTO();
        rs.setId(employee.getId());
        rs.setName(employee.getName());
        rs.setSkills(employee.getSkills());
        rs.setDaysAvailable(employee.getDaysAvailable());

        return rs;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);

        EmployeeDTO rs = new EmployeeDTO();
        rs.setId(employee.getId());
        rs.setName(employee.getName());
        rs.setSkills(employee.getSkills());
        rs.setDaysAvailable(employee.getDaysAvailable());

        return rs;
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);

        Set<DayOfWeek> utpDaysAvailable = new HashSet<>();
        if (employee.getDaysAvailable() != null && !employee.getDaysAvailable().isEmpty()) {
            utpDaysAvailable.addAll(employee.getDaysAvailable());
        }
        utpDaysAvailable.addAll(daysAvailable);
        employee.setDaysAvailable(utpDaysAvailable);
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeRepository.findEmployeesForService(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills(), employeeDTO.getSkills().size());
        List<EmployeeDTO> rs = new ArrayList<>();
        rs = employees.stream().map(el -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(el.getId());
            dto.setName(el.getName());
            dto.setSkills(el.getSkills());
            dto.setDaysAvailable(el.getDaysAvailable());
            return dto;
        }).collect(Collectors.toList());

        return rs;
    }
}
