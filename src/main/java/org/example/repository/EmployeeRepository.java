package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Optional<Employee> findByEmail(String email);
}
