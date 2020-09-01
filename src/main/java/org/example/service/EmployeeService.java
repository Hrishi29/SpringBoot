package org.example.service;

import org.example.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(String id);
    Employee create(Employee employee);
    Employee update(String id, Employee employee);
    Employee delete(String id);

}
