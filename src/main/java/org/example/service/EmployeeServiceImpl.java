package org.example.service;

import org.example.entity.Employee;
import org.example.exception.EmployeeConflictException;
import org.example.exception.EmployeeNotFoundException;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override

    public List<Employee> findAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Employee findById(String id) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if(!existing.isPresent())
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exist.");
        return existing.get();
    }

    @Override
    public Employee create(Employee employee) {
        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());
        if(existing.isPresent())
            throw new EmployeeConflictException("Employee with email " + employee.getEmail() + " already exist.");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(String id, Employee employee) {
        Optional<Employee> checkId = employeeRepository.findById(id);
        if(!checkId.isPresent())
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exist.");
        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());
        if(existing.isPresent()){
            if(!existing.get().getId().equals(id))
                throw new EmployeeConflictException("Employee with email " + employee.getEmail() + " already exist.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee delete(String id) {
        Optional<Employee> checkId = employeeRepository.findById(id);
        if(!checkId.isPresent())
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exist.");
        employeeRepository.delete(checkId.get());
        return checkId.get();
    }
}
