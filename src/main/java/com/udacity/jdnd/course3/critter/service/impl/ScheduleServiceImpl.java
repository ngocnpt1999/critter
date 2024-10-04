package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        ScheduleDTO rs = new ScheduleDTO();

        List<Pet> pets = petRepository.getPetsByIdsJoinSchedules(scheduleDTO.getPetIds());
        List<Employee> employees = employeeRepository.getEmployeesByIdsJoinSchedules(scheduleDTO.getEmployeeIds());
        schedule.setDay(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        for (Pet p : pets) {
            p.getSchedules().add(schedule);
        }
        schedule.setPets(pets);
        for (Employee em : employees) {
            em.getSchedules().add(schedule);
        }
        schedule.setEmployees(employees);
        schedule = scheduleRepository.save(schedule);

        rs.setId(schedule.getId());
        rs.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        rs.setDate(schedule.getDay());
        rs.setActivities(schedule.getActivities());
        rs.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

        return rs;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDTO> rs = new ArrayList<>();
        rs = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDay());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());

        return rs;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> schedules = scheduleRepository.getScheduleByEmployeeId(employeeId);
        List<ScheduleDTO> rs = new ArrayList<>();
        rs = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDay());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());

        return rs;
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(long petId) {
        List<Schedule> schedules = scheduleRepository.getScheduleByPetId(petId);
        List<ScheduleDTO> rs = new ArrayList<>();
        rs = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDay());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());

        return rs;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        List<Schedule> schedules = scheduleRepository.getScheduleByCustomerIds(petIds);
        List<ScheduleDTO> rs = new ArrayList<>();
        rs = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDay());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());

        return rs;
    }
}
