package org.example.service;


import org.example.entity.Employee;
import org.example.exception.EmployeeConflictException;
import org.example.exception.EmployeeNotFoundException;
import org.example.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class SpringTestConfiguration{

        @Bean
        public EmployeeService getService(){
            return new EmployeeServiceImpl();
        }
    }
    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;

    private List<Employee> employeeList;

    @Before
    public void setUp() {
        Employee emp = new Employee();
        emp.setEmail("hrishi.gad@gmail.com");
        emp.setFirstName("Hrishi");
        emp.setLastName("Gadkari");

        Employee emp2 = new Employee();
        emp2.setEmail("john.doe@gmail.com");
        emp2.setFirstName("John");
        emp2.setLastName("Doe");

        Employee emp3 = new Employee();
        emp3.setEmail("hrishi.gad@gmail.com");
        emp3.setFirstName("Mary");
        emp3.setLastName("Jane");

        employeeList = new ArrayList<>(Arrays.asList(emp, emp2, emp3));
        Mockito.when(repository.findAll()).thenReturn(employeeList);
        Mockito.when(repository.findById(emp.getId())).thenReturn(employeeList.stream().findFirst());
        Mockito.when(repository.findById(emp3.getId())).thenReturn(Optional.of(emp3));
        Mockito.when(repository.findByEmail("john.doe@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(repository.findByEmail("hrishi.gad@gmail.com")).thenReturn(Optional.of(emp));
        Mockito.when(repository.save(emp)).thenReturn(emp);
        Mockito.when(repository.save(emp2)).thenReturn(emp2);
    }

    @Test
    public void findAll() {
        List<Employee> result  = service.findAll();
        Assert.assertEquals("Employee list should not be empty", employeeList, result);
    }

    @Test
    public void findById() {
        Employee result = service.findById(employeeList.get(0).getId());
        Assert.assertEquals("Employee should exist", employeeList.get(0), result);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void findNoneById() {
        Employee result = service.findById("NotValid");
    }

    @Test
    public void create() {
        Employee result = service.create(employeeList.get(1));
        Assert.assertEquals("Employee saved successfully", employeeList.get(1), result);
    }

    @Test(expected = EmployeeConflictException.class)
    public void createExist() {
        Employee result = service.create(employeeList.get(0));
    }

    @Test
    public void update() {
        Employee result = service.update(employeeList.get(0).getId(), employeeList.get(0));
        Assert.assertEquals("Employee updated sucessfully", employeeList.get(0), result);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void updateNotFound() {
        Employee result = service.update("fgkfgg", employeeList.get(0));
    }

    @Test(expected = EmployeeConflictException.class)
    public void updateEmailConflict() {
        Employee result = service.update(employeeList.get(2).getId(), employeeList.get(2));
    }

    @Test
    public void delete() {
        Employee result = service.delete(employeeList.get(2).getId());
        Assert.assertEquals("Employee deleted successfully", employeeList.get(2), result);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void deleteNotFound() {
        Employee result = service.delete("RandonUuid");
    }
}